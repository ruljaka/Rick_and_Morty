package com.ruslangrigoriev.rickandmorty.presentation.characters

import android.content.Context
import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
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
import com.ruslangrigoriev.rickandmorty.common.navigator
import com.ruslangrigoriev.rickandmorty.common.showToast
import com.ruslangrigoriev.rickandmorty.databinding.FragmentCharactersBinding
import com.ruslangrigoriev.rickandmorty.presentation.characterDetails.CharacterDetailsFragment
import com.ruslangrigoriev.rickandmorty.presentation.characters.CharactersFilterDialog.Companion.CHARACTERS_DIALOG_FILTER_ARG
import com.ruslangrigoriev.rickandmorty.presentation.characters.CharactersFilterDialog.Companion.CHARACTERS_DIALOG_REQUEST_KEY
import com.ruslangrigoriev.rickandmorty.presentation.characters.adapters.CharactersPagingAdapter
import com.ruslangrigoriev.rickandmorty.presentation.common.FragmentNavigator
import com.ruslangrigoriev.rickandmorty.presentation.common.LoaderStateAdapter
import com.ruslangrigoriev.rickandmorty.presentation.main.MainActivity
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.cancellable
import java.util.*
import javax.inject.Inject

class CharactersFragment : Fragment(R.layout.fragment_characters) {

    @Inject
    lateinit var viewModel: CharactersViewModel
    private val binding: FragmentCharactersBinding by viewBinding()
    private var navigator: FragmentNavigator? = null
    private var searchQuery: String? = null
    private lateinit var pagingAdapter: CharactersPagingAdapter
    private var collectingJob: Job? = null

    override fun onAttach(context: Context) {
        super.onAttach(context)
        context.appComponent.inject(this)
        navigator = context.navigator
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        (activity as MainActivity).supportActionBar?.setDisplayHomeAsUpEnabled(false)
        (activity as MainActivity).supportActionBar?.title = "Characters"
        createMenu()
        initRecyclerView()
        initSwipeToRefresh()
        initSearch()
        subscribeUI()
    }

    private fun subscribeUI() {
        collectingJob?.cancel()
        collectingJob = lifecycleScope.launchWhenStarted {
            viewModel.charactersFlow?.cancellable()?.collect { pagingData ->
                if (searchQuery.isNullOrEmpty()) {
                    pagingAdapter.submitData(pagingData)
                } else {
                    val query = searchQuery!!.lowercase(Locale.getDefault()).trim()
                    val data = pagingData.filter {
                        it.name.lowercase(Locale.getDefault()).contains(query)
                                || it.species.lowercase(Locale.getDefault()).contains(query)
                    }
                    pagingAdapter.submitData(data)
                }
            }
        }
    }

    private fun initRecyclerView() {
        pagingAdapter = CharactersPagingAdapter { id -> onListItemClick(id) }
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
        binding.charactersRecView.apply {
            layoutManager = gridLM
            adapter = pagingAdapter.withLoadStateFooter(loaderStateAdapter)
        }

        pagingAdapter.addLoadStateListener { loadState ->
            binding.charactersSwipeContainer.isRefreshing = loadState.refresh is LoadState.Loading
            if (loadState.refresh is LoadState.Error)
                (loadState.refresh as LoadState.Error).error.message?.showToast(requireContext())
            binding.nothingCharactersTextView.isVisible =
                loadState.append.endOfPaginationReached && pagingAdapter.itemCount < 1
        }
    }

    private fun initSwipeToRefresh() {
        with(binding) {
            charactersSwipeContainer.setColorSchemeColors(
                resources.getColor(
                    R.color.atlantis,
                    null
                )
            )
            charactersSwipeContainer.setOnRefreshListener {
                viewModel.getCharacters()
                subscribeUI()
            }
        }
    }

    private fun initSearch() {
        binding.charactersSearchView.setOnQueryTextListener(
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

    private fun showFilter() {
        val dialog = CharactersFilterDialog.newInstance(viewModel.charactersFilter)
        dialog.show(childFragmentManager, null)

        childFragmentManager.setFragmentResultListener(
            CHARACTERS_DIALOG_REQUEST_KEY,
            viewLifecycleOwner
        ) { _, bundle ->
            val filter = bundle.getSerializable(CHARACTERS_DIALOG_FILTER_ARG) as CharactersFilter
            viewModel.getCharacters(filter)
            subscribeUI()
        }
    }

    private fun onListItemClick(id: Int) {
        navigator?.navigate(
            CharacterDetailsFragment.newInstance(id),
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
}