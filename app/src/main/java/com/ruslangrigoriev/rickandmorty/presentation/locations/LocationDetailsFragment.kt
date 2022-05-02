package com.ruslangrigoriev.rickandmorty.presentation.locations

import android.content.Context
import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import by.kirich1409.viewbindingdelegate.viewBinding
import com.ruslangrigoriev.rickandmorty.R
import com.ruslangrigoriev.rickandmorty.databinding.FragmentLocationDetailsBinding
import com.ruslangrigoriev.rickandmorty.presentation.FragmentNavigator
import com.ruslangrigoriev.rickandmorty.presentation.MainActivity

class LocationDetailsFragment : Fragment(R.layout.fragment_location_details) {
    private var navigator: FragmentNavigator? = null
    private val binding: FragmentLocationDetailsBinding by viewBinding()
    private val viewModel: LocationDetailsViewModel by viewModels()

    override fun onAttach(context: Context) {
        super.onAttach(context)
        if (context is FragmentNavigator) {
            navigator = context
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        (activity as MainActivity).supportActionBar?.setDisplayHomeAsUpEnabled(true)
        (activity as MainActivity).supportActionBar?.title = "Location Details"
    }
}