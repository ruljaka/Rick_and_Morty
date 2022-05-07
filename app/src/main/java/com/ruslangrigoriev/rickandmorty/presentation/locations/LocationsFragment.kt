package com.ruslangrigoriev.rickandmorty.presentation.locations

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
import com.ruslangrigoriev.rickandmorty.databinding.FragmentLocationsBinding
import com.ruslangrigoriev.rickandmorty.presentation.common.*
import com.ruslangrigoriev.rickandmorty.presentation.locationDetails.LocationDetailsFragment
import com.ruslangrigoriev.rickandmorty.presentation.locations.LocationsFilterDialog.Companion.LOCATIONS_DIALOG_FILTER_ARG
import com.ruslangrigoriev.rickandmorty.presentation.locations.LocationsFilterDialog.Companion.LOCATIONS_DIALOG_REQUEST_KEY
import com.ruslangrigoriev.rickandmorty.presentation.locations.adapters.LocationsPagingAdapter
import com.ruslangrigoriev.rickandmorty.presentation.main.MainActivity
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

    override fun onAttach(context: Context) {
        super.onAttach(context)
        context.appComponent.inject(this)
        navigator = context.navigator
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        (activity as MainActivity).supportActionBar?.setDisplayHomeAsUpEnabled(false)
        (activity as MainActivity).supportActionBar?.title = "Locations"
        createMenu()
        initRecyclerView()
        initSwipeToRefresh()
        initSearch()
        subscribeUI()
    }

    private fun subscribeUI() {
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
                return if (position == pagingAdapter.itemCount && loaderStateAdapter.itemCount > 0) {
                    2
                } else {
                    1
                }
            }
        }
        binding.locationsRecyclerView.apply {
            layoutManager = gridLM
            adapter = pagingAdapter.withLoadStateFooter(loaderStateAdapter)
        }

        pagingAdapter.addLoadStateListener { loadState ->
            binding.locationsSwipeContainer.isRefreshing = loadState.refresh is LoadState.Loading
            if (loadState.refresh is LoadState.Error)
                (loadState.refresh as LoadState.Error).error.message?.showToast(requireContext())
            binding.locationsNothingTextView.isVisible =
                loadState.append.endOfPaginationReached && pagingAdapter.itemCount < 1
        }
    }

    private fun initSwipeToRefresh() {
        with(binding) {
            locationsSwipeContainer.setColorSchemeColors(
                resources.getColor(
                    R.color.atlantis,
                    null
                )
            )
            locationsSwipeContainer.setOnRefreshListener {
                filter = null
                viewModel.getLocations()
                subscribeUI()
            }
        }
    }

    private fun initSearch() {
        binding.locationsSearchView.setOnQueryTextListener(object :
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

    private fun showFilter() {
        val dialog = LocationsFilterDialog.newInstance(filter)
        dialog.show(childFragmentManager, null)

        childFragmentManager.setFragmentResultListener(
            LOCATIONS_DIALOG_REQUEST_KEY,
            viewLifecycleOwner
        ) { _, bundle ->
            filter =
                bundle.getSerializable(LOCATIONS_DIALOG_FILTER_ARG) as LocationsFilter
            viewModel.getLocations(filter)
            subscribeUI()
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