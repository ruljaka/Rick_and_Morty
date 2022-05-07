package com.ruslangrigoriev.rickandmorty.presentation.locationDetails

import android.content.Context
import android.os.Bundle
import android.view.View
import androidx.core.os.bundleOf
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import by.kirich1409.viewbindingdelegate.viewBinding
import com.ruslangrigoriev.rickandmorty.R
import com.ruslangrigoriev.rickandmorty.databinding.FragmentLocationDetailsBinding
import com.ruslangrigoriev.rickandmorty.domain.model.LocationModel
import com.ruslangrigoriev.rickandmorty.presentation.characterDetails.CharacterDetailsFragment
import com.ruslangrigoriev.rickandmorty.presentation.characters.adapters.CharactersAdapter
import com.ruslangrigoriev.rickandmorty.presentation.common.FragmentNavigator
import com.ruslangrigoriev.rickandmorty.presentation.common.appComponent
import com.ruslangrigoriev.rickandmorty.presentation.common.navigator
import com.ruslangrigoriev.rickandmorty.presentation.common.showToast
import com.ruslangrigoriev.rickandmorty.presentation.main.MainActivity
import javax.inject.Inject

class LocationDetailsFragment : Fragment(R.layout.fragment_location_details) {

    @Inject
    lateinit var viewModel: LocationDetailsViewModel
    private val binding: FragmentLocationDetailsBinding by viewBinding()
    private var navigator: FragmentNavigator? = null
    private lateinit var residentsAdapter: CharactersAdapter
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
        (activity as MainActivity).supportActionBar?.setDisplayHomeAsUpEnabled(true)
        (activity as MainActivity).supportActionBar?.title = "Location Details"
        initRecycler()
        initSwipeToRefresh()
        fetchData()
    }

    private fun fetchData() {
        viewModel.fetchLocation(locationId)
        viewModel.loading.observe(viewLifecycleOwner) {
            binding.swipeLocDet.isRefreshing = it
            binding.layoutLocDet.isVisible = !it
        }
        viewModel.data.observe(viewLifecycleOwner) {
            bindUi(it)
        }
        viewModel.error.observe(viewLifecycleOwner) {
            it?.showToast(requireContext())
            binding.layoutLocDet.isVisible = false
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
            swipeLocDet.setColorSchemeColors(
                resources.getColor(
                    R.color.atlantis,
                    null
                )
            )
            swipeLocDet.setOnRefreshListener {
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