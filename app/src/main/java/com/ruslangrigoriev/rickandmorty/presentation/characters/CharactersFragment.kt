package com.ruslangrigoriev.rickandmorty.presentation.characters

import android.content.Context
import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.widget.ArrayAdapter
import android.widget.AutoCompleteTextView
import android.widget.Button
import androidx.core.view.MenuHost
import androidx.core.view.MenuProvider
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import by.kirich1409.viewbindingdelegate.viewBinding
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.ruslangrigoriev.rickandmorty.R
import com.ruslangrigoriev.rickandmorty.databinding.FragmentCharactersBinding
import com.ruslangrigoriev.rickandmorty.presentation.FragmentNavigator
import com.ruslangrigoriev.rickandmorty.presentation.MainActivity

class CharactersFragment : Fragment(R.layout.fragment_characters) {
    private var navigator: FragmentNavigator? = null
    private val binding: FragmentCharactersBinding by viewBinding()
    private val viewModel: CharactersViewModel by viewModels()

    override fun onAttach(context: Context) {
        super.onAttach(context)
        if (context is FragmentNavigator) {
            navigator = context
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        (activity as MainActivity).supportActionBar?.setDisplayHomeAsUpEnabled(false)
        (activity as MainActivity).supportActionBar?.title = "Characters"
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

    private fun showFilter() {
        val dialog = BottomSheetDialog(requireContext())
        dialog.setContentView(R.layout.dialog_filter_characters)

        val statusAdapter = ArrayAdapter(
            requireContext(),
            R.layout.item_dropdown_menu,
            resources.getStringArray(R.array.status)
        )
        val statusSpinner = dialog.findViewById<AutoCompleteTextView>(R.id.status_ch_dg_act)
        statusSpinner?.setAdapter(statusAdapter)
        val genderAdapter = ArrayAdapter(
            requireContext(),
            R.layout.item_dropdown_menu,
            resources.getStringArray(R.array.gender)
        )
        val genderSpinner = dialog.findViewById<AutoCompleteTextView>(R.id.gender_ch_dg_act)
        genderSpinner?.setAdapter(genderAdapter)
        val okBtn = dialog.findViewById<Button>(R.id.filter_char_btn)
        okBtn?.setOnClickListener {
            dialog.dismiss()
        }
        dialog.show()
    }

}