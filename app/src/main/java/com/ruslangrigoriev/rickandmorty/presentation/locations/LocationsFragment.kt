package com.ruslangrigoriev.rickandmorty.presentation.locations

import android.content.Context
import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import android.view.*
import android.widget.Button
import androidx.fragment.app.Fragment
import androidx.core.view.MenuHost
import androidx.core.view.MenuProvider
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import by.kirich1409.viewbindingdelegate.viewBinding
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.ruslangrigoriev.rickandmorty.R
import com.ruslangrigoriev.rickandmorty.databinding.FragmentEpisodesBinding
import com.ruslangrigoriev.rickandmorty.databinding.FragmentLocationsBinding
import com.ruslangrigoriev.rickandmorty.presentation.FragmentNavigator
import com.ruslangrigoriev.rickandmorty.presentation.MainActivity
import com.ruslangrigoriev.rickandmorty.presentation.episodes.EpisodesViewModel

class LocationsFragment : Fragment(R.layout.fragment_locations) {
    private var navigator: FragmentNavigator? = null
    private val binding: FragmentLocationsBinding by viewBinding()
    private val viewModel: LocationsViewModel by viewModels()

    override fun onAttach(context: Context) {
        super.onAttach(context)
        if (context is FragmentNavigator) {
            navigator = context
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        (activity as MainActivity).supportActionBar?.setDisplayHomeAsUpEnabled(false)
        (activity as MainActivity).supportActionBar?.title = "Locations"
        createMenu()

    }

    private fun createMenu() {
        val menuHost: MenuHost = requireActivity()
        menuHost.addMenuProvider(object : MenuProvider {
            override fun onCreateMenu(menu: Menu, menuInflater: MenuInflater) {
                menuInflater.inflate(R.menu.menu_filter, menu)
            }
            override fun onMenuItemSelected(menuItem: MenuItem): Boolean {
                return when (menuItem.itemId) {
                    R.id.filter -> {
                        showFilter()
                        true
                    }
                    else -> false
                }
            }
        }, viewLifecycleOwner, Lifecycle.State.STARTED)
    }

    private fun showFilter(){
        val dialog = BottomSheetDialog(requireContext())
        dialog.setContentView(R.layout.dialog_filter_locations)
        val okBtn = dialog.findViewById<Button>(R.id.filter_loc_btn)
        okBtn?.setOnClickListener {
            dialog.dismiss()
        }
        dialog.show()
    }

}