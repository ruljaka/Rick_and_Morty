package com.ruslangrigoriev.rickandmorty.presentation.adapters

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.ruslangrigoriev.rickandmorty.data.dto.episodeDTO.EpisodeDTO
import com.ruslangrigoriev.rickandmorty.databinding.ItemEpisodeBinding

class EpisodeViewHolder(
    private val view: View,
    private val onItemClicked: (id: Int) -> Unit,
) : RecyclerView.ViewHolder(view) {

    fun bind(episode: EpisodeDTO) {
        val binding = ItemEpisodeBinding.bind(view)

        with(binding) {
            root.setOnClickListener {
                onItemClicked(episode.id)
            }
            episodeNameTextView.text = episode.name
            episodeNumberTextView.text = episode.episode
            episodeDateTextView.text = episode.airDate
        }
    }
}