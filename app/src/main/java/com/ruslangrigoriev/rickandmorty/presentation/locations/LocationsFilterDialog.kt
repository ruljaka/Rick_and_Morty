package com.ruslangrigoriev.rickandmorty.presentation.locations

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.fragment.app.setFragmentResult
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.ruslangrigoriev.rickandmorty.R
import com.ruslangrigoriev.rickandmorty.databinding.DialogFilterLocationsBinding

class LocationsFilterDialog : BottomSheetDialogFragment() {

    lateinit var binding: DialogFilterLocationsBinding
    private val savedFilter: LocationsFilter?
        get() = requireArguments().getSerializable(LOCATIONS_DIALOG_FILTER_ARG) as LocationsFilter?

    companion object {
        const val LOCATIONS_DIALOG_FILTER_ARG = "LOCATIONS_DIALOG_FILTER_ARG"
        const val LOCATIONS_DIALOG_REQUEST_KEY = "LOCATIONS_DIALOG_REQUEST_KEY"

        @JvmStatic
        fun newInstance(savedFilter: LocationsFilter?) =
            LocationsFilterDialog().apply {
                arguments = bundleOf(LOCATIONS_DIALOG_FILTER_ARG to savedFilter)
            }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        binding = DialogFilterLocationsBinding.bind(
            inflater.inflate(
                R.layout.dialog_filter_locations,
                container
            )
        )
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        with(binding) {
            savedFilter?.let {
                nameLocDgEditText.setText(it.name)
                typeLocDgEditText.setText(it.type)
                dimensionLocDgEditText.setText(it.dimension)
            }
            resetLocBtn.setOnClickListener {
                nameLocDgEditText.text = null
                typeLocDgEditText.text = null
                dimensionLocDgEditText.text = null
            }
            filterLocBtn.setOnClickListener {
                val newFilter = LocationsFilter(
                    name = if (nameLocDgEditText.text.isNullOrEmpty()) null
                    else nameLocDgEditText.text.toString().trim(),
                    type = if (typeLocDgEditText.text.isNullOrEmpty()) null
                    else typeLocDgEditText.text.toString(),
                    dimension = if (dimensionLocDgEditText.text.isNullOrEmpty()) null
                    else dimensionLocDgEditText.text.toString(),
                )
                setFragmentResult(
                    LOCATIONS_DIALOG_REQUEST_KEY,
                    bundleOf(LOCATIONS_DIALOG_FILTER_ARG to newFilter)
                )
                dismiss()
            }
        }
    }
}