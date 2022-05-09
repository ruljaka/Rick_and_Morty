package com.ruslangrigoriev.rickandmorty.presentation.ui.characters

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import androidx.core.os.bundleOf
import androidx.fragment.app.setFragmentResult
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.ruslangrigoriev.rickandmorty.R
import com.ruslangrigoriev.rickandmorty.databinding.DialogFilterCharactersBinding

class CharactersFilterDialog : BottomSheetDialogFragment() {

    lateinit var binding: DialogFilterCharactersBinding
    private val savedFilter: CharactersFilter?
        get() = requireArguments().getSerializable(CHARACTERS_DIALOG_FILTER_ARG) as CharactersFilter?

    companion object {
        const val CHARACTERS_DIALOG_FILTER_ARG = "CHARACTERS_DIALOG_FILTER_ARG"
        const val CHARACTERS_DIALOG_REQUEST_KEY = "CHARACTERS_DIALOG_REQUEST_KEY"

        @JvmStatic
        fun newInstance(savedFilter: CharactersFilter?) =
            CharactersFilterDialog().apply {
                arguments = bundleOf(CHARACTERS_DIALOG_FILTER_ARG to savedFilter)
            }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = DialogFilterCharactersBinding.bind(
            inflater.inflate(
                R.layout.dialog_filter_characters,
                container
            )
        )
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        with(binding) {
            val statusAdapter = ArrayAdapter(
                requireContext(),
                R.layout.item_dropdown_menu,
                resources.getStringArray(R.array.status)
            )
            statusChDgAct.setAdapter(statusAdapter)
            val genderAdapter = ArrayAdapter(
                requireContext(),
                R.layout.item_dropdown_menu,
                resources.getStringArray(R.array.gender)
            )
            genderChDgAct.setAdapter(genderAdapter)
            savedFilter?.let {
                nameChDgEditText.setText(it.name)
                statusChDgAct.setText(it.status, false)
                speciesChDgEditText.setText(it.species)
                typeChDgEditText.setText(it.type)
                genderChDgAct.setText(it.gender, false)
            }
            resetCharBtn.setOnClickListener {
                nameChDgEditText.text = null
                statusChDgAct.setText(null, false)
                speciesChDgEditText.text = null
                typeChDgEditText.text = null
                genderChDgAct.setText(null, false)
            }
            filterCharBtn.setOnClickListener {
                val newFilter = CharactersFilter(
                    name = if (nameChDgEditText.text.isNullOrEmpty()) null
                    else nameChDgEditText.text.toString().trim(),
                    status = if (statusChDgAct.text.isNullOrEmpty()) null
                    else statusChDgAct.text.toString(),
                    species = if (speciesChDgEditText.text.isNullOrEmpty()) null
                    else speciesChDgEditText.text.toString().trim(),
                    type = if (typeChDgEditText.text.isNullOrEmpty()) null
                    else typeChDgEditText.text.toString().trim(),
                    gender = if (genderChDgAct.text.isNullOrEmpty()) null
                    else genderChDgAct.text.toString()
                )
                setFragmentResult(
                    CHARACTERS_DIALOG_REQUEST_KEY,
                    bundleOf(CHARACTERS_DIALOG_FILTER_ARG to newFilter)
                )
                dismiss()
            }
        }
    }
}