package com.ruslangrigoriev.rickandmorty.presentation.characterDetails

import android.content.Context
import android.os.Bundle
import android.view.View
import androidx.core.os.bundleOf
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import by.kirich1409.viewbindingdelegate.viewBinding
import coil.load
import com.ruslangrigoriev.rickandmorty.R
import com.ruslangrigoriev.rickandmorty.databinding.FragmentCharacterDetailsBinding
import com.ruslangrigoriev.rickandmorty.domain.model.CharacterModel
import com.ruslangrigoriev.rickandmorty.presentation.common.FragmentNavigator
import com.ruslangrigoriev.rickandmorty.presentation.common.appComponent
import com.ruslangrigoriev.rickandmorty.presentation.common.navigator
import com.ruslangrigoriev.rickandmorty.presentation.common.showToast
import com.ruslangrigoriev.rickandmorty.presentation.episodeDetails.EpisodeDetailsFragment
import com.ruslangrigoriev.rickandmorty.presentation.episodes.adapters.EpisodesAdapter
import com.ruslangrigoriev.rickandmorty.presentation.locationDetails.LocationDetailsFragment
import com.ruslangrigoriev.rickandmorty.presentation.main.MainActivity
import javax.inject.Inject

class CharacterDetailsFragment : Fragment(R.layout.fragment_character_details) {

    @Inject
    lateinit var viewModel: CharacterDetailsViewModel
    private val binding: FragmentCharacterDetailsBinding by viewBinding()
    private var navigator: FragmentNavigator? = null
    private lateinit var episodesAdapter: EpisodesAdapter
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
        (activity as MainActivity).supportActionBar?.setDisplayHomeAsUpEnabled(true)
        (activity as MainActivity).supportActionBar?.title = "Character Details"
        initRecycler()
        initSwipeToRefresh()
        fetchData()
    }

    private fun fetchData() {
        viewModel.fetchCharacter(characterId)
        viewModel.loading.observe(viewLifecycleOwner) {
            binding.swipeChDet.isRefreshing = it
            binding.layoutChDet.isVisible = !it
        }
        viewModel.data.observe(viewLifecycleOwner) {
            bindUi(it)
        }
        viewModel.error.observe(viewLifecycleOwner) {
            binding.layoutChDet.isVisible = false
            it?.showToast(requireContext())
        }
    }

    private fun initRecycler() {
        episodesAdapter = EpisodesAdapter { id -> onListItemClick(id) }
        binding.recyclerChDet.adapter = episodesAdapter
    }

    private fun bindUi(character: CharacterModel) {
        with(binding) {
            nameChDetTv.text = getString(R.string.character_name, character.name)
            speciesChDetTv.text = getString(R.string.character_species, character.species)
            typeChDetTv.text =
                if (character.type.isNotEmpty()) getString(R.string.character_type, character.type)
                else getString(R.string.character_type, "-")
            genderChDetTv.text = getString(R.string.character_gender, character.gender)
            originChDetTv.text = getString(R.string.character_origin, character.originName)
            character.originID?.let {
                originChDetTv.apply {
                    setTextColor(resources.getColor(R.color.atlantis, null))
                    setOnClickListener {
                        onLocationClick(character.originID)
                    }
                }
            }
            locationChDetTv.text = getString(R.string.character_location, character.locationName)
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
            episodesAdapter.setData(character.episodes)
        }
    }

    private fun initSwipeToRefresh() {
        with(binding) {
            swipeChDet.setColorSchemeColors(
                resources.getColor(
                    R.color.atlantis,
                    null
                )
            )
            swipeChDet.setOnRefreshListener {
                fetchData()
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