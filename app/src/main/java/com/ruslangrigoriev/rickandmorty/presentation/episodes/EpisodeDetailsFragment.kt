package com.ruslangrigoriev.rickandmorty.presentation.episodes

import android.content.Context
import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import by.kirich1409.viewbindingdelegate.viewBinding
import com.ruslangrigoriev.rickandmorty.R
import com.ruslangrigoriev.rickandmorty.databinding.FragmentEpisodeDetailsBinding
import com.ruslangrigoriev.rickandmorty.presentation.FragmentNavigator
import com.ruslangrigoriev.rickandmorty.presentation.MainActivity

class EpisodeDetailsFragment : Fragment(R.layout.fragment_episode_details) {
    private var navigator: FragmentNavigator? = null
    private val binding: FragmentEpisodeDetailsBinding by viewBinding()
    private val viewModel: EpisodeDetailsViewModel by viewModels()

    override fun onAttach(context: Context) {
        super.onAttach(context)
        if (context is FragmentNavigator) {
            navigator = context
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        (activity as MainActivity).supportActionBar?.setDisplayHomeAsUpEnabled(true)
        (activity as MainActivity).supportActionBar?.title = "Episode Details"
    }

}