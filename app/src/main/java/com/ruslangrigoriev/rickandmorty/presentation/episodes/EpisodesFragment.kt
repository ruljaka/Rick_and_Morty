package com.ruslangrigoriev.rickandmorty.presentation.episodes

import android.content.Context
import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SearchView
import androidx.core.view.MenuHost
import androidx.core.view.MenuProvider
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.paging.LoadState
import androidx.paging.filter
import androidx.recyclerview.widget.GridLayoutManager
import by.kirich1409.viewbindingdelegate.viewBinding
import com.ruslangrigoriev.rickandmorty.R
import com.ruslangrigoriev.rickandmorty.common.appComponent
import com.ruslangrigoriev.rickandmorty.common.showToast
import com.ruslangrigoriev.rickandmorty.databinding.FragmentEpisodesBinding
import com.ruslangrigoriev.rickandmorty.presentation.FragmentNavigator
import com.ruslangrigoriev.rickandmorty.presentation.adapters.LoaderStateAdapter
import com.ruslangrigoriev.rickandmorty.presentation.episodeDetails.EpisodeDetailsFragment
import com.ruslangrigoriev.rickandmorty.presentation.episodes.adapters.EpisodesPagingAdapter
import com.ruslangrigoriev.rickandmorty.presentation.main.MainActivity
import java.util.*
import javax.inject.Inject

class EpisodesFragment : Fragment(R.layout.fragment_episodes) {
    @Inject
    lateinit var navigator: FragmentNavigator
    @Inject
    lateinit var viewModel: EpisodesViewModel
    private val binding: FragmentEpisodesBinding by viewBinding()
    private var searchQuery: String? = null
    private lateinit var pagingAdapter: EpisodesPagingAdapter

    override fun onAttach(context: Context) {
        super.onAttach(context)
        context.appComponent.inject(this)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        (activity as MainActivity).supportActionBar?.setDisplayHomeAsUpEnabled(false)
        (activity as MainActivity).supportActionBar?.title = "Episodes"
        createMenu()
        initRecyclerView()
        initSwipeToRefresh()
        initSearch()
        subscribeUI()
    }

    private fun subscribeUI() {
        lifecycleScope.launchWhenStarted {
            viewModel.episodesFlow?.collect { pagingData ->
                if (searchQuery.isNullOrEmpty()) {
                    pagingAdapter.submitData(pagingData)
                } else {
                    val query = searchQuery!!.lowercase(Locale.getDefault()).trim()
                    val data = pagingData.filter {
                        it.name.lowercase(Locale.getDefault()).contains(query)
                                || it.episode.lowercase(Locale.getDefault()).contains(query)
                    }
                    pagingAdapter.submitData(data)
                }
            }
        }
    }

    private fun initRecyclerView() {
        pagingAdapter = EpisodesPagingAdapter { id -> onListItemClick(id) }
        val gridLM = GridLayoutManager(activity, 2)
        val loaderStateAdapter = LoaderStateAdapter { pagingAdapter.retry() }
        gridLM.spanSizeLookup = object : GridLayoutManager.SpanSizeLookup() {
            override fun getSpanSize(position: Int): Int {
                return if (position == pagingAdapter.itemCount && loaderStateAdapter.itemCount > 0) {
                    2
                } else {
                    1
                }
            }
        }
        binding.episodesRecyclerView.apply {
            layoutManager = gridLM
            adapter = pagingAdapter.withLoadStateFooter(loaderStateAdapter)
        }

        pagingAdapter.addLoadStateListener { loadState ->
            binding.episodesSwipeContainer.isRefreshing = loadState.refresh is LoadState.Loading
            if (loadState.refresh is LoadState.Error)
                (loadState.refresh as LoadState.Error).error.message?.showToast(requireContext())
            binding.nothingEpisodesTextView.isVisible =loadState.append.endOfPaginationReached && pagingAdapter.itemCount < 1
        }
    }

    private fun initSwipeToRefresh() {
        with(binding) {
            episodesSwipeContainer.setColorSchemeColors(
                resources.getColor(
                    R.color.atlantis,
                    null
                )
            )
            episodesSwipeContainer.setOnRefreshListener {
                viewModel.getEpisodes()
                subscribeUI()
            }
        }
    }

    private fun initSearch() {
        binding.episodesSearchView.setOnQueryTextListener(object :
            SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                searchQuery = query
                pagingAdapter.refresh()
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                searchQuery = newText
                pagingAdapter.refresh()
                return false
            }
        })
    }

    private fun onListItemClick(id: Int) {
        navigator.navigate(
            requireActivity() as AppCompatActivity,
            EpisodeDetailsFragment.newInstance(id),
            true
        )
    }

    private fun createMenu() {
        val menuHost: MenuHost = requireActivity()
        menuHost.addMenuProvider(object : MenuProvider {
            override fun onCreateMenu(menu: Menu, menuInflater: MenuInflater) {
                menuInflater.inflate(R.menu.menu_filter, menu)
            }

            override fun onMenuItemSelected(menuItem: MenuItem): Boolean {
                return when (menuItem.itemId) {
                    R.id.filter -> {
                        showFilter()
                        true
                    }
                    else -> false
                }
            }
        }, viewLifecycleOwner, Lifecycle.State.STARTED)
    }

    private fun showFilter() {
        val dialog = EpisodesFilterDialog.newInstance(viewModel.episodesFilter)
        dialog.show(childFragmentManager, null)

        childFragmentManager.setFragmentResultListener(
            EpisodesFilterDialog.EPISODES_DIALOG_REQUEST_KEY,
            viewLifecycleOwner
        ) { _, bundle ->
            val filter =
                bundle.getSerializable(EpisodesFilterDialog.EPISODES_DIALOG_FILTER_ARG) as EpisodesFilter
            viewModel.getEpisodes(filter)
            subscribeUI()
        }
    }
}