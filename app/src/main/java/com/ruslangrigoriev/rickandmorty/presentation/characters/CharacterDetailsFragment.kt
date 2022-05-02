package com.ruslangrigoriev.rickandmorty.presentation.characters

import android.content.Context
import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import by.kirich1409.viewbindingdelegate.viewBinding
import com.ruslangrigoriev.rickandmorty.R
import com.ruslangrigoriev.rickandmorty.databinding.FragmentCharacterDetailsBinding
import com.ruslangrigoriev.rickandmorty.presentation.FragmentNavigator
import com.ruslangrigoriev.rickandmorty.presentation.MainActivity

class CharacterDetailsFragment : Fragment(R.layout.fragment_character_details) {
    private var navigator: FragmentNavigator? = null
    private val binding: FragmentCharacterDetailsBinding by viewBinding()
    private val viewModel: CharacterDetailsViewModel by viewModels()

    override fun onAttach(context: Context) {
        super.onAttach(context)
        if (context is FragmentNavigator) {
            navigator = context
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        (activity as MainActivity).supportActionBar?.setDisplayHomeAsUpEnabled(true)
        (activity as MainActivity).supportActionBar?.title = "Character Details"

    }
}