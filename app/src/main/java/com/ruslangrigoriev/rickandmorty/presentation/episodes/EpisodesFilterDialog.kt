package com.ruslangrigoriev.rickandmorty.presentation.episodes

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.fragment.app.setFragmentResult
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.ruslangrigoriev.rickandmorty.R
import com.ruslangrigoriev.rickandmorty.databinding.DialogFilterEpisodesBinding

class EpisodesFilterDialog : BottomSheetDialogFragment() {

    lateinit var binding: DialogFilterEpisodesBinding
    private val savedFilter: EpisodesFilter?
        get() = requireArguments().getSerializable(EPISODES_DIALOG_FILTER_ARG) as EpisodesFilter?

    companion object {
        const val EPISODES_DIALOG_FILTER_ARG = "EPISODES_DIALOG_FILTER_ARG"
        const val EPISODES_DIALOG_REQUEST_KEY = "EPISODES_DIALOG_REQUEST_KEY"

        @JvmStatic
        fun newInstance(savedFilter: EpisodesFilter?) =
            EpisodesFilterDialog().apply {
                arguments = bundleOf(EPISODES_DIALOG_FILTER_ARG to savedFilter)
            }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DialogFilterEpisodesBinding.bind(
            inflater.inflate(
                R.layout.dialog_filter_episodes,
                container
            )
        )
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        with(binding) {

            savedFilter?.let {
                nameEpDgEditText.setText(it.name)
                episodeEpDgEditText.setText(it.episode)
            }
            resetEpBtn.setOnClickListener {
                nameEpDgEditText.text = null
                episodeEpDgEditText.text = null
            }
            filterEpBtn.setOnClickListener {
                val newFilter = EpisodesFilter(
                    name = if (nameEpDgEditText.text.isNullOrEmpty()) null
                    else nameEpDgEditText.text.toString().trim(),
                    episode = if (episodeEpDgEditText.text.isNullOrEmpty()) null
                    else episodeEpDgEditText.text.toString(),
                )
                setFragmentResult(
                    EPISODES_DIALOG_REQUEST_KEY,
                    bundleOf(EPISODES_DIALOG_FILTER_ARG to newFilter)
                )
                dismiss()
            }
        }
    }
}