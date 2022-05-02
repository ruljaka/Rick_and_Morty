package com.ruslangrigoriev.rickandmorty.presentation.episodeDetails

import android.content.Context
import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import by.kirich1409.viewbindingdelegate.viewBinding
import com.ruslangrigoriev.rickandmorty.R
import com.ruslangrigoriev.rickandmorty.common.appComponent
import com.ruslangrigoriev.rickandmorty.databinding.FragmentEpisodeDetailsBinding
import com.ruslangrigoriev.rickandmorty.presentation.FragmentNavigator
import com.ruslangrigoriev.rickandmorty.presentation.MainActivity
import javax.inject.Inject

class EpisodeDetailsFragment : Fragment(R.layout.fragment_episode_details) {
    @Inject
    lateinit var navigator: FragmentNavigator
    @Inject
    lateinit var viewModel: EpisodeDetailsViewModel
    private val binding: FragmentEpisodeDetailsBinding by viewBinding()


    override fun onAttach(context: Context) {
        super.onAttach(context)
        context.appComponent.inject(this)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        (activity as MainActivity).supportActionBar?.setDisplayHomeAsUpEnabled(true)
        (activity as MainActivity).supportActionBar?.title = "Episode Details"
    }

}