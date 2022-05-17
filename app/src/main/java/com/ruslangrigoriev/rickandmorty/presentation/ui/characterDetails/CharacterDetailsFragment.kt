package com.ruslangrigoriev.rickandmorty.presentation.ui.characterDetails

import android.content.Context
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.ActionBar
import androidx.core.os.bundleOf
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import by.kirich1409.viewbindingdelegate.viewBinding
import coil.load
import com.ruslangrigoriev.rickandmorty.R
import com.ruslangrigoriev.rickandmorty.databinding.FragmentCharacterDetailsBinding
import com.ruslangrigoriev.rickandmorty.presentation.common.FragmentNavigator
import com.ruslangrigoriev.rickandmorty.presentation.common.appComponent
import com.ruslangrigoriev.rickandmorty.presentation.common.navigator
import com.ruslangrigoriev.rickandmorty.presentation.common.showToast
import com.ruslangrigoriev.rickandmorty.presentation.model.CharacterModel
import com.ruslangrigoriev.rickandmorty.presentation.ui.episodeDetails.EpisodeDetailsFragment
import com.ruslangrigoriev.rickandmorty.presentation.ui.episodes.adapters.EpisodesAdapter
import com.ruslangrigoriev.rickandmorty.presentation.ui.locationDetails.LocationDetailsFragment
import com.ruslangrigoriev.rickandmorty.presentation.ui.main.MainActivity
import javax.inject.Inject

class CharacterDetailsFragment : Fragment(R.layout.fragment_character_details) {

    @Inject
    lateinit var viewModel: CharacterDetailsViewModel
    private val binding: FragmentCharacterDetailsBinding by viewBinding()
    private var navigator: FragmentNavigator? = null
    private lateinit var episodesAdapter: EpisodesAdapter
    private val toolbar: ActionBar?
        get() = (activity as MainActivity).supportActionBar
    private val characterId: Int
        get() = requireArguments().getInt(CHARACTER_ID)

    companion object {
        private const val CHARACTER_ID = "CHARACTER_ID"

        @JvmStatic
        fun newInstance(characterId: Int) =
            CharacterDetailsFragment().apply {
                arguments = bundleOf(CHARACTER_ID to characterId)
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
        toolbar?.title = "Character"
        initSwipeToRefresh()
        fetchData()
    }

    private fun fetchData() {
        if (viewModel.data.value == null) {
            viewModel.fetchCharacter(characterId)
        }
        viewModel.loading.observe(viewLifecycleOwner) { isLoading ->
            with(binding) {
                progressBarChDet.isVisible = isLoading && !refresherChDet.isRefreshing
                layoutChDet.isVisible = !isLoading
                if (isLoading == false) refresherChDet.isRefreshing = isLoading
            }
        }
        viewModel.data.observe(viewLifecycleOwner) { character ->
            bindUi(character)
        }
        viewModel.error.observe(viewLifecycleOwner) { message ->
            message?.let{showToast(requireContext(),it)}
        }
    }

    private fun bindUi(character: CharacterModel) {
        binding.apply {
            nameChDetTv.text = getString(R.string.name, character.name)
            speciesChDetTv.text = getString(R.string.species, character.species)
            typeChDetTv.text =
                if (character.type.isNotEmpty()) getString(R.string.type, character.type)
                else getString(R.string.type, "-")
            genderChDetTv.text = getString(R.string.gender, character.gender)
            originChDetTv.text = getString(R.string.origin, character.originName)
            character.originID?.let {
                originChDetTv.apply {
                    setTextColor(resources.getColor(R.color.atlantis, null))
                    setOnClickListener {
                        onLocationClick(character.originID)
                    }
                }
            }
            locationChDetTv.text = getString(R.string.location, character.locationName)
            character.locationID?.let {
                locationChDetTv.apply {
                    setTextColor(resources.getColor(R.color.atlantis, null))
                    setOnClickListener {
                        onLocationClick(character.locationID)
                    }
                }
            }
            imageChDetImv.load(character.image) {
                placeholder(R.drawable.placeholder)
                error(R.drawable.placeholder)
            }
            when (character.status) {
                "Alive" -> {
                    statusChDetImv.setImageResource(R.drawable.icon_status_alive)
                }
                "Dead" -> {
                    statusChDetImv.setImageResource(R.drawable.icon_status_dead)
                }
                else -> {
                    statusChDetImv.setImageResource(R.drawable.icon_status_unknown)
                }
            }
            episodesAdapter = EpisodesAdapter(character.episodes) { id -> onListItemClick(id) }
            binding.recyclerChDet.adapter = episodesAdapter
        }
    }

    private fun initSwipeToRefresh() {
        with(binding) {
            refresherChDet.setColorSchemeColors(
                resources.getColor(
                    R.color.atlantis,
                    null
                )
            )
            refresherChDet.setOnRefreshListener {
                viewModel.fetchCharacter(characterId)
            }
        }
    }


    private fun onListItemClick(id: Int) {
        navigator?.navigate(
            EpisodeDetailsFragment.newInstance(id),
            true
        )
    }

    private fun onLocationClick(id: Int) {
        navigator?.navigate(
            LocationDetailsFragment.newInstance(id),
            true
        )
    }
}