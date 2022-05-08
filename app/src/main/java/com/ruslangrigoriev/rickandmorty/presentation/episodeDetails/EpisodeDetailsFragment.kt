package com.ruslangrigoriev.rickandmorty.presentation.episodeDetails

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
import com.ruslangrigoriev.rickandmorty.domain.model.EpisodeModel
import com.ruslangrigoriev.rickandmorty.presentation.characterDetails.CharacterDetailsFragment
import com.ruslangrigoriev.rickandmorty.presentation.characters.adapters.CharactersAdapter
import com.ruslangrigoriev.rickandmorty.presentation.common.FragmentNavigator
import com.ruslangrigoriev.rickandmorty.presentation.common.appComponent
import com.ruslangrigoriev.rickandmorty.presentation.common.navigator
import com.ruslangrigoriev.rickandmorty.presentation.common.showToast
import com.ruslangrigoriev.rickandmorty.presentation.main.MainActivity
import javax.inject.Inject

class EpisodeDetailsFragment : Fragment(R.layout.fragment_episode_details) {

    @Inject
    lateinit var viewModel: EpisodeDetailsViewModel
    private val binding: FragmentEpisodeDetailsBinding by viewBinding()
    private var navigator: FragmentNavigator? = null
    private lateinit var charactersAdapter: CharactersAdapter
    private var isLoaded: Boolean = false
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
        initRecycler()
        initSwipeToRefresh()
        fetchData()
    }

    private fun fetchData() {
        if (!isLoaded) {
            viewModel.fetchEpisode(episodeId)
            isLoaded = true
        }
        viewModel.loading.observe(viewLifecycleOwner) {
            with(binding) {
                progressBarEpDet.isVisible = it && !refresherEpDet.isRefreshing
                layoutEpDet.isVisible = !it
                if (it == false) refresherEpDet.isRefreshing = it
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

    private fun initSwipeToRefresh() {
        with(binding) {
            refresherEpDet.setColorSchemeColors(
                resources.getColor(
                    R.color.atlantis,
                    null
                )
            )
            refresherEpDet.setOnRefreshListener {
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