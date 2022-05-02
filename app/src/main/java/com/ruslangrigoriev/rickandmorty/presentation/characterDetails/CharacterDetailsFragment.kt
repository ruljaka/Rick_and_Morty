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
import com.ruslangrigoriev.rickandmorty.presentation.RequestState
import com.ruslangrigoriev.rickandmorty.common.appComponent
import com.ruslangrigoriev.rickandmorty.common.showToast
import com.ruslangrigoriev.rickandmorty.databinding.FragmentCharacterDetailsBinding
import com.ruslangrigoriev.rickandmorty.domain.model.CharacterModel
import com.ruslangrigoriev.rickandmorty.presentation.FragmentNavigator
import com.ruslangrigoriev.rickandmorty.presentation.MainActivity
import com.ruslangrigoriev.rickandmorty.presentation.adapters.EpisodesAdapter
import javax.inject.Inject

class CharacterDetailsFragment : Fragment(R.layout.fragment_character_details) {

    @Inject
    lateinit var navigator: FragmentNavigator
    @Inject
    lateinit var viewModel: CharacterDetailsViewModel
    private val binding: FragmentCharacterDetailsBinding by viewBinding()
    private val characterId: Int
        get() = requireArguments().getInt(CHARACTER_ID)
    private lateinit var episodesAdapter: EpisodesAdapter

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
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        (activity as MainActivity).supportActionBar?.setDisplayHomeAsUpEnabled(true)
        (activity as MainActivity).supportActionBar?.title = "Character Details"
        fetchData()
        initRecycler()
    }

    private fun fetchData() {
        viewModel.fetchCharacter(characterId)
        viewModel.requestState.observe(this) { response ->
            when (response) {
                is RequestState.Success -> {
                    binding.characterProgressBar.isVisible = false
                    response.data?.let { bindUi(it) }
                }
                is RequestState.Error -> {
                    binding.characterProgressBar.isVisible = false
                    response.message?.showToast(requireContext())
                }
                is RequestState.Loading -> {
                    binding.characterProgressBar.isVisible = true
                }
            }
        }
    }

    private fun initRecycler() {
        episodesAdapter = EpisodesAdapter { id -> onListItemClick(id) }
        binding.recyclerChDet.adapter = episodesAdapter
    }

    private fun bindUi(character: CharacterModel) {
        with(binding) {
            with(character) {
                nameChDetTv.text = getString(R.string.character_name, name)
                speciesChDetTv.text = getString(R.string.character_species, species)
                typeChDetTv.text = getString(R.string.character_type, type)
                genderChDetTv.text = getString(R.string.character_gender, gender)
                originChDetTv.text = getString(R.string.character_origin, originName)
                locationChDetTv.text = getString(R.string.character_location, locationName)
                imageChDetImv.load(image)
                when (status) {
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
    }

    private fun onListItemClick(id: Int) {
//        navigator.navigate(
//            requireActivity() as AppCompatActivity,
//            ?????????
//            true
//        )
    }
}