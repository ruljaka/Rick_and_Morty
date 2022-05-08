package com.ruslangrigoriev.rickandmorty.presentation.episodes

import android.content.Context
import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import androidx.appcompat.app.ActionBar
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
import com.ruslangrigoriev.rickandmorty.databinding.FragmentEpisodesBinding
import com.ruslangrigoriev.rickandmorty.presentation.common.*
import com.ruslangrigoriev.rickandmorty.presentation.episodeDetails.EpisodeDetailsFragment
import com.ruslangrigoriev.rickandmorty.presentation.episodes.EpisodesFilterDialog.Companion.EPISODES_DIALOG_FILTER_ARG
import com.ruslangrigoriev.rickandmorty.presentation.episodes.EpisodesFilterDialog.Companion.EPISODES_DIALOG_REQUEST_KEY
import com.ruslangrigoriev.rickandmorty.presentation.episodes.adapters.EpisodesPagingAdapter
import com.ruslangrigoriev.rickandmorty.presentation.main.MainActivity
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.cancellable
import java.util.*
import javax.inject.Inject

class EpisodesFragment : Fragment(R.layout.fragment_episodes) {

    @Inject
    lateinit var viewModel: EpisodesViewModel
    private val binding: FragmentEpisodesBinding by viewBinding()
    private var navigator: FragmentNavigator? = null
    private var collectingJob: Job? = null
    private lateinit var pagingAdapter: EpisodesPagingAdapter
    private var searchQuery: String? = null
    var filter: EpisodesFilter? = null
    private val toolbar: ActionBar?
        get() = (activity as MainActivity).supportActionBar

    override fun onAttach(context: Context) {
        super.onAttach(context)
        context.appComponent.inject(this)
        navigator = context.navigator
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        toolbar?.setDisplayHomeAsUpEnabled(false)
        toolbar?.title = "Episodes"
        createMenu()
        initRecyclerView()
        initSwipeToRefresh()
        initSearch()
        subscribeUI()
    }

    private fun subscribeUI() {
        collectingJob?.cancel()
        collectingJob = lifecycleScope.launchWhenStarted {
            viewModel.episodesFlow?.cancellable()?.collect { pagingData ->
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
        binding.episodesRefresher.isRefreshing = false
    }

    private fun initRecyclerView() {
        pagingAdapter = EpisodesPagingAdapter { id -> onEpisodeClick(id) }
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

        pagingAdapter.addLoadStateListener {
            binding.episodesProgressBar.isVisible = it.refresh is LoadState.Loading
                    && !binding.episodesRefresher.isRefreshing
            if (it.refresh is LoadState.Error)
                "Failed to load data \nTry refresh".showToast(requireContext())
            binding.episodesNothingTextView.isVisible =
                it.append.endOfPaginationReached && pagingAdapter.itemCount < 1
        }
    }

    private fun initSwipeToRefresh() {
        with(binding) {
            episodesRefresher.setColorSchemeColors(resources.getColor(R.color.atlantis, null))
            episodesRefresher.setOnRefreshListener {
                searchQuery = null
                filter = null
                viewModel.getEpisodes()
                subscribeUI()
                binding.episodesSearchView.apply {
                    setQuery(null, false)
                    clearFocus()
                    onActionViewCollapsed()
                }
            }
        }
    }

    private fun initSearch() {
        binding.episodesSearchView.setOnQueryTextListener(
            object : SearchView.OnQueryTextListener {
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

    private fun onEpisodeClick(id: Int) {
        navigator?.navigate(
            EpisodeDetailsFragment.newInstance(id),
            true
        )
    }

    private fun showFilter() {
        val dialog = EpisodesFilterDialog.newInstance(filter)
        dialog.show(childFragmentManager, null)

        childFragmentManager.setFragmentResultListener(
            EPISODES_DIALOG_REQUEST_KEY,
            viewLifecycleOwner
        ) { _, bundle ->
            filter = bundle.getSerializable(EPISODES_DIALOG_FILTER_ARG) as EpisodesFilter
            viewModel.getEpisodes(filter)
            subscribeUI()
        }
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

}