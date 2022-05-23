package com.ruslangrigoriev.rickandmorty.presentation.ui.locationDetails

import android.content.Context
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.ActionBar
import androidx.core.os.bundleOf
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import by.kirich1409.viewbindingdelegate.viewBinding
import com.ruslangrigoriev.rickandmorty.R
import com.ruslangrigoriev.rickandmorty.databinding.FragmentLocationDetailsBinding
import com.ruslangrigoriev.rickandmorty.presentation.common.FragmentNavigator
import com.ruslangrigoriev.rickandmorty.presentation.common.appComponent
import com.ruslangrigoriev.rickandmorty.presentation.common.navigator
import com.ruslangrigoriev.rickandmorty.presentation.common.showToast
import com.ruslangrigoriev.rickandmorty.presentation.model.LocationModel
import com.ruslangrigoriev.rickandmorty.presentation.ui.characterDetails.CharacterDetailsFragment
import com.ruslangrigoriev.rickandmorty.presentation.ui.characters.adapters.CharactersAdapter
import com.ruslangrigoriev.rickandmorty.presentation.ui.episodeDetails.EpisodeDetailsViewModel
import com.ruslangrigoriev.rickandmorty.presentation.ui.main.MainActivity
import javax.inject.Inject

class LocationDetailsFragment : Fragment(R.layout.fragment_location_details) {

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory
    private val viewModel: LocationDetailsViewModel by viewModels{viewModelFactory}
    private val binding: FragmentLocationDetailsBinding by viewBinding()
    private var navigator: FragmentNavigator? = null
    private lateinit var residentsAdapter: CharactersAdapter
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
        initSwipeToRefresh()
        fetchData()
    }

    private fun fetchData() {
        if (viewModel.data.value == null) {
            viewModel.fetchLocation(locationId)
        }
        viewModel.loading.observe(viewLifecycleOwner) { isLoading ->
            with(binding) {
                progressBarLocDet.isVisible = isLoading && !refresherLocDet.isRefreshing
                layoutLocDet.isVisible = !isLoading
                if (isLoading == false) refresherLocDet.isRefreshing = isLoading
            }
        }
        viewModel.data.observe(viewLifecycleOwner) { location ->
            bindUi(location)
        }
        viewModel.error.observe(viewLifecycleOwner) { message ->
            message?.let{showToast(requireContext(),it)}
        }
    }

    private fun bindUi(location: LocationModel) {
        binding.apply {
            nameLocDetTv.text = location.name
            typeLocDetTv.text = location.type
            dimensionLocDetTv.text = location.dimension
            residentsAdapter = CharactersAdapter(location.residents) { id -> onListItemClick(id) }
            binding.recyclerLocDet.adapter = residentsAdapter
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
                viewModel.fetchLocation(locationId)
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