package com.ruslangrigoriev.rickandmorty.presentation.ui.locationDetails

import android.content.Context
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.ActionBar
import androidx.core.os.bundleOf
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import by.kirich1409.viewbindingdelegate.viewBinding
import com.ruslangrigoriev.rickandmorty.R
import com.ruslangrigoriev.rickandmorty.databinding.FragmentLocationDetailsBinding
import com.ruslangrigoriev.rickandmorty.presentation.model.LocationModel
import com.ruslangrigoriev.rickandmorty.presentation.ui.characterDetails.CharacterDetailsFragment
import com.ruslangrigoriev.rickandmorty.presentation.ui.characters.adapters.CharactersAdapter
import com.ruslangrigoriev.rickandmorty.presentation.common.FragmentNavigator
import com.ruslangrigoriev.rickandmorty.presentation.common.appComponent
import com.ruslangrigoriev.rickandmorty.presentation.common.navigator
import com.ruslangrigoriev.rickandmorty.presentation.common.showToast
import com.ruslangrigoriev.rickandmorty.presentation.ui.main.MainActivity
import javax.inject.Inject

class LocationDetailsFragment : Fragment(R.layout.fragment_location_details) {

    @Inject
    lateinit var viewModel: LocationDetailsViewModel
    private val binding: FragmentLocationDetailsBinding by viewBinding()
    private var navigator: FragmentNavigator? = null
    private lateinit var residentsAdapter: CharactersAdapter
    private var isLoaded: Boolean = false
    private val toolbar: ActionBar?
        get() = (activity as MainActivity).supportActionBar
    private val locationId: Int
        get() = requireArguments().getInt(LOCATION_ID)

    companion object {
        private const val LOCATION_ID = "LOCATION_ID"

        @JvmStatic
        fun newInstance(locationId: Int) =
            LocationDetailsFragment().apply {
                arguments = bundleOf(LOCATION_ID to locationId)
            }
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        context.appComponent.inject(this)
        navigator = context.navigator
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        toolbar?.setDisplayHomeAsUpEnabled(true)
        toolbar?.title = "Location"
        initRecycler()
        initSwipeToRefresh()
        fetchData()
    }

    private fun fetchData() {
        if (!isLoaded) {
            viewModel.fetchLocation(locationId)
            isLoaded = true
        }
        viewModel.loading.observe(viewLifecycleOwner) {
            with(binding) {
                progressBarLocDet.isVisible = it && !refresherLocDet.isRefreshing
                layoutLocDet.isVisible = !it
                if (it == false) refresherLocDet.isRefreshing = it
            }
        }
        viewModel.data.observe(viewLifecycleOwner) {
            bindUi(it)
        }
        viewModel.error.observe(viewLifecycleOwner) {
            it?.showToast(requireContext())
        }
    }

    private fun initRecycler() {
        residentsAdapter = CharactersAdapter { id -> onListItemClick(id) }
        binding.recyclerLocDet.adapter = residentsAdapter
    }

    private fun bindUi(location: LocationModel) {
        with(binding) {
            nameLocDetTv.text = location.name
            typeLocDetTv.text = location.type
            dimensionLocDetTv.text = location.dimension
            residentsAdapter.setData(location.residents)
        }
    }

    private fun initSwipeToRefresh() {
        with(binding) {
            refresherLocDet.setColorSchemeColors(
                resources.getColor(
                    R.color.atlantis,
                    null
                )
            )
            refresherLocDet.setOnRefreshListener {
                isLoaded = false
                viewModel.loading.removeObservers(viewLifecycleOwner)
                viewModel.data.removeObservers(viewLifecycleOwner)
                viewModel.error.removeObservers(viewLifecycleOwner)
                fetchData()
            }
        }
    }

    private fun onListItemClick(id: Int) {
        navigator?.navigate(
            CharacterDetailsFragment.newInstance(id),
            true
        )
    }
}