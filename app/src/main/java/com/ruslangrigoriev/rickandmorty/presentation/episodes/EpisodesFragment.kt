package com.ruslangrigoriev.rickandmorty.presentation.episodes

import android.content.Context
import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.widget.Button
import androidx.core.view.MenuHost
import androidx.core.view.MenuProvider
import androidx.fragment.app.Fragment
import androidx.lifecycle.Lifecycle
import by.kirich1409.viewbindingdelegate.viewBinding
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.ruslangrigoriev.rickandmorty.R
import com.ruslangrigoriev.rickandmorty.common.appComponent
import com.ruslangrigoriev.rickandmorty.databinding.FragmentEpisodesBinding
import com.ruslangrigoriev.rickandmorty.presentation.FragmentNavigator
import com.ruslangrigoriev.rickandmorty.presentation.main.MainActivity
import javax.inject.Inject

class EpisodesFragment : Fragment(R.layout.fragment_episodes) {
    @Inject
    lateinit var navigator: FragmentNavigator
    @Inject
    lateinit var viewModel: EpisodesViewModel
    private val binding: FragmentEpisodesBinding by viewBinding()

    override fun onAttach(context: Context) {
        super.onAttach(context)
        context.appComponent.inject(this)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        (activity as MainActivity).supportActionBar?.setDisplayHomeAsUpEnabled(false)
        (activity as MainActivity).supportActionBar?.title = "Episodes"
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
        dialog.setContentView(R.layout.dialog_filter_episodes)
        val okBtn = dialog.findViewById<Button>(R.id.filter_ep_btn)
        okBtn?.setOnClickListener {
            dialog.dismiss()
        }
        dialog.show()
    }
}