package com.ruslangrigoriev.rickandmorty.presentation.episodeDetails

import android.content.Context
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.core.os.bundleOf
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import by.kirich1409.viewbindingdelegate.viewBinding
import coil.load
import com.ruslangrigoriev.rickandmorty.R
import com.ruslangrigoriev.rickandmorty.common.appComponent
import com.ruslangrigoriev.rickandmorty.common.showToast
import com.ruslangrigoriev.rickandmorty.databinding.FragmentEpisodeDetailsBinding
import com.ruslangrigoriev.rickandmorty.domain.model.CharacterModel
import com.ruslangrigoriev.rickandmorty.domain.model.EpisodeModel
import com.ruslangrigoriev.rickandmorty.presentation.FragmentNavigator
import com.ruslangrigoriev.rickandmorty.presentation.RequestState
import com.ruslangrigoriev.rickandmorty.presentation.characterDetails.CharacterDetailsFragment
import com.ruslangrigoriev.rickandmorty.presentation.episodes.adapters.CharactersAdapter
import com.ruslangrigoriev.rickandmorty.presentation.main.MainActivity
import javax.inject.Inject

class EpisodeDetailsFragment : Fragment(R.layout.fragment_episode_details) {

    @Inject
    lateinit var navigator: FragmentNavigator
    @Inject
    lateinit var viewModel: EpisodeDetailsViewModel
    private val binding: FragmentEpisodeDetailsBinding by viewBinding()
    private val characterId: Int
        get() = requireArguments().getInt(EPISODE_ID)
    private lateinit var charactersAdapter: CharactersAdapter

    companion object {
        private const val EPISODE_ID = "EPISODE_ID"

        @JvmStatic
        fun newInstance(episodeId: Int) =
            EpisodeDetailsFragment().apply {
                arguments = bundleOf(EPISODE_ID to episodeId)
            }
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        context.appComponent.inject(this)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        (activity as MainActivity).supportActionBar?.setDisplayHomeAsUpEnabled(true)
        (activity as MainActivity).supportActionBar?.title = "Episode Details"
        fetchData()
        initRecycler()
    }

    private fun fetchData() {
        viewModel.fetchCharacter(characterId)
        viewModel.requestState.observe(this) { state ->
            when (state) {
                is RequestState.Success -> {
                    binding.episodeProgressBar.isVisible = false
                    state.data?.let { bindUi(it) }
                }
                is RequestState.Error -> {
                    binding.episodeProgressBar.isVisible = false
                    state.message?.showToast(requireContext())
                }
                is RequestState.Loading -> {
                    binding.episodeProgressBar.isVisible = true
                }
            }
        }
    }

    private fun initRecycler() {
        charactersAdapter = CharactersAdapter { id -> onListItemClick(id) }
        binding.recyclerEpDet.adapter = charactersAdapter
    }

    private fun bindUi(episode: EpisodeModel) {
        with(binding) {
                nameEpDetTv.text = episode.name
                dateEpDetTv.text = episode.airDate
                episodeEpDetTv.text = episode.episode
                charactersAdapter.setData(episode.characters)
        }
    }

    private fun onListItemClick(id: Int) {
        navigator.navigate(
            requireActivity() as AppCompatActivity,
            CharacterDetailsFragment.newInstance(id),
            true
        )
    }

}