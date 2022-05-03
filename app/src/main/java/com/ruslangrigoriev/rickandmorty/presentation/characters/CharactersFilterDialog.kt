package com.ruslangrigoriev.rickandmorty.presentation.characters

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
import java.util.*

class CharactersFilterDialog : BottomSheetDialogFragment() {

    lateinit var binding: DialogFilterCharactersBinding
    private val filter: CharactersFilter?
        get() = requireArguments().getSerializable(CHARACTERS_DIALOG_ARG) as CharactersFilter?

    companion object {
        const val CHARACTERS_DIALOG_ARG = "CHARACTERS_DIALOG_ARG"
        const val CHARACTERS_DIALOG_REQUEST_KEY = "CHARACTERS_DIALOG_REQUEST_KEY"

        @JvmStatic
        fun newInstance(filter: CharactersFilter?) =
            CharactersFilterDialog().apply {
                arguments = bundleOf(CHARACTERS_DIALOG_ARG to filter)
            }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
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
            filter?.let {
                nameChDgEditText.setText(it.name)
                statusChDgAct.setText(it.status,false)
                speciesChDgEditText.setText(it.species)
                typeChDgEditText.setText(it.type)
                genderChDgAct.setText(it.gender,false)
            }
            filterCharBtn.setOnClickListener {
                val newFilter = CharactersFilter(
                    name = nameChDgEditText.text.toString().lowercase(Locale.getDefault()).trim(),
                    status = statusChDgAct.text.toString(),
                    species = speciesChDgEditText.text.toString().lowercase(Locale.getDefault()).trim(),
                    type = typeChDgEditText.text.toString().lowercase(Locale.getDefault()).trim(),
                    gender = genderChDgAct.text.toString()
                )
                setFragmentResult(
                    CHARACTERS_DIALOG_REQUEST_KEY,
                    bundleOf(CHARACTERS_DIALOG_ARG to newFilter)
                )
                dismiss()
            }
            resetCharBtn.setOnClickListener {
                nameChDgEditText.text = null
                statusChDgAct.setText(null,false)
                speciesChDgEditText.text = null
                typeChDgEditText.text = null
                genderChDgAct.setText(null,false)
            }
        }
    }
}