package com.ruslangrigoriev.rickandmorty.presentation.ui.episodeDetails

import android.content.Context
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.ActionBar
import androidx.core.os.bundleOf
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import by.kirich1409.viewbindingdelegate.viewBinding
import com.ruslangrigoriev.rickandmorty.R
import com.ruslangrigoriev.rickandmorty.databinding.FragmentEpisodeDetailsBinding
import com.ruslangrigoriev.rickandmorty.presentation.common.FragmentNavigator
import com.ruslangrigoriev.rickandmorty.presentation.common.appComponent
import com.ruslangrigoriev.rickandmorty.presentation.common.navigator
import com.ruslangrigoriev.rickandmorty.presentation.common.showToast
import com.ruslangrigoriev.rickandmorty.presentation.model.EpisodeModel
import com.ruslangrigoriev.rickandmorty.presentation.ui.characterDetails.CharacterDetailsFragment
import com.ruslangrigoriev.rickandmorty.presentation.ui.characters.adapters.CharactersAdapter
import com.ruslangrigoriev.rickandmorty.presentation.ui.main.MainActivity
import javax.inject.Inject

class EpisodeDetailsFragment : Fragment(R.layout.fragment_episode_details) {

    @Inject
    lateinit var viewModel: EpisodeDetailsViewModel
    private val binding: FragmentEpisodeDetailsBinding by viewBinding()
    private var navigator: FragmentNavigator? = null
    private lateinit var charactersAdapter: CharactersAdapter
    private val toolbar: ActionBar?
        get() = (activity as MainActivity).supportActionBar
    private val episodeId: Int
        get() = requireArguments().getInt(EPISODE_ID)

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
        navigator = context.navigator
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        toolbar?.setDisplayHomeAsUpEnabled(true)
        toolbar?.title = "Episode"
        initSwipeToRefresh()
        fetchData()
    }

    private fun fetchData() {
        if (viewModel.data.value == null) {
            viewModel.fetchEpisode(episodeId)
        }
        viewModel.loading.observe(viewLifecycleOwner) { isLoading ->
            with(binding) {
                progressBarEpDet.isVisible = isLoading && !refresherEpDet.isRefreshing
                layoutEpDet.isVisible = !isLoading
                if (isLoading == false) refresherEpDet.isRefreshing = isLoading
            }
        }
        viewModel.data.observe(viewLifecycleOwner) { episode ->
            bindUi(episode)
        }
        viewModel.error.observe(viewLifecycleOwner) { message ->
            message?.let { showToast(requireContext(), it) }
        }
    }

    private fun bindUi(episode: EpisodeModel) {
        binding.apply {
            nameEpDetTv.text = episode.name
            dateEpDetTv.text = episode.airDate
            episodeEpDetTv.text = episode.episode
            charactersAdapter = CharactersAdapter(episode.characters) { id -> onListItemClick(id) }
            binding.recyclerEpDet.adapter = charactersAdapter
        }
    }

    private fun initSwipeToRefresh() {
        with(binding) {
            refresherEpDet.setColorSchemeColors(
                resources.getColor(
                    R.color.atlantis,
                    null
                )
            )
            refresherEpDet.setOnRefreshListener {
                viewModel.fetchEpisode(episodeId)
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