package com.ruslangrigoriev.rickandmorty.presentation.ui.locations

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
import com.ruslangrigoriev.rickandmorty.databinding.FragmentLocationsBinding
import com.ruslangrigoriev.rickandmorty.presentation.common.*
import com.ruslangrigoriev.rickandmorty.presentation.ui.locationDetails.LocationDetailsFragment
import com.ruslangrigoriev.rickandmorty.presentation.ui.locations.LocationsFilterDialog.Companion.LOCATIONS_DIALOG_FILTER_ARG
import com.ruslangrigoriev.rickandmorty.presentation.ui.locations.LocationsFilterDialog.Companion.LOCATIONS_DIALOG_REQUEST_KEY
import com.ruslangrigoriev.rickandmorty.presentation.ui.locations.adapters.LocationsPagingAdapter
import com.ruslangrigoriev.rickandmorty.presentation.ui.main.MainActivity
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.cancellable
import java.util.*
import javax.inject.Inject

class LocationsFragment : Fragment(R.layout.fragment_locations) {

    @Inject
    lateinit var viewModel: LocationsViewModel
    private val binding: FragmentLocationsBinding by viewBinding()
    private var navigator: FragmentNavigator? = null
    private var collectingJob: Job? = null
    private lateinit var pagingAdapter: LocationsPagingAdapter
    private var searchQuery: String? = null
    var filter: LocationsFilter? = null
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
        toolbar?.title = "Locations"
        createMenu()
        initRecyclerView()
        initSwipeToRefresh()
        initSearch()
        collectData()
    }

    private fun collectData() {
        collectingJob?.cancel()
        collectingJob = lifecycleScope.launchWhenStarted {
            viewModel.locationsFlow?.cancellable()?.collect { pagingData ->
                if (searchQuery.isNullOrEmpty()) {
                    pagingAdapter.submitData(pagingData)
                } else {
                    val query = searchQuery!!.lowercase(Locale.getDefault()).trim()
                    val data = pagingData.filter {
                        it.name.lowercase(Locale.getDefault()).contains(query)
                                || it.type.lowercase(Locale.getDefault()).contains(query)
                                || it.dimension.lowercase(Locale.getDefault()).contains(query)
                    }
                    pagingAdapter.submitData(data)
                }
            }
        }
    }

    private fun initRecyclerView() {
        pagingAdapter = LocationsPagingAdapter { id -> onListItemClick(id) }
        val gridLM = GridLayoutManager(activity, 2)
        val loaderStateAdapter = LoaderStateAdapter { pagingAdapter.retry() }
        gridLM.spanSizeLookup = object : GridLayoutManager.SpanSizeLookup() {
            override fun getSpanSize(position: Int): Int {
                return if (position == pagingAdapter.itemCount && loaderStateAdapter.itemCount > 0) { 2 } else { 1 }
            }
        }
        binding.locationsRecyclerView.apply {
            layoutManager = gridLM
            adapter = pagingAdapter.withLoadStateFooter(loaderStateAdapter)
        }
        pagingAdapter.addLoadStateListener { loadState ->
            val isLoading = loadState.refresh is LoadState.Loading
            val isError = loadState.refresh is LoadState.Error
            val isPagingEnded = loadState.append.endOfPaginationReached
            binding.locationsRefresher.isRefreshing = isLoading
            binding.locationsNothingTextView.isVisible =
                isPagingEnded && pagingAdapter.itemCount == 0
            if (isError) showToast(requireContext(), getString(R.string.error_message))
        }
    }

    private fun initSwipeToRefresh() {
        with(binding) {
            locationsRefresher.setColorSchemeColors(resources.getColor(R.color.atlantis, null))
            locationsRefresher.setOnRefreshListener {
                filter = null
                resetSearch()
                refreshData(filter)
            }
        }
    }

    private fun initSearch() {
        binding.locationsSearchView.apply {
            setOnCloseListener {
                if(!searchQuery.isNullOrEmpty()) {
                    searchQuery = null
                    refreshData(filter)
                }
                false
            }
            setOnQueryTextFocusChangeListener { _, hasFocus ->
                if (hasFocus) {
                    setOnQueryTextListener(
                        object : SearchView.OnQueryTextListener {
                            override fun onQueryTextSubmit(query: String?): Boolean {
                                searchQuery = query
                                refreshData(filter)
                                return false
                            }

                            override fun onQueryTextChange(newText: String?): Boolean {
                                searchQuery = newText
                                refreshData(filter)
                                return false
                            }
                        })
                }
            }
        }
    }

    private fun showFilter() {
        val dialog = LocationsFilterDialog.newInstance(filter)
        dialog.show(childFragmentManager, null)

        childFragmentManager.setFragmentResultListener(
            LOCATIONS_DIALOG_REQUEST_KEY,
            viewLifecycleOwner
        )
        { _, bundle ->
            filter = bundle.getSerializable(LOCATIONS_DIALOG_FILTER_ARG) as LocationsFilter
            resetSearch()
            refreshData(filter)
        }
    }

    private fun refreshData(filter: LocationsFilter?) {
        viewModel.getLocations(filter)
        collectData()
    }

    private fun resetSearch() {
        binding.locationsSearchView.apply {
            searchQuery = null
            setQuery(null, false)
            clearFocus()
            onActionViewCollapsed()
        }
    }

    private fun onListItemClick(id: Int) {
        navigator?.navigate(
            LocationDetailsFragment.newInstance(id),
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