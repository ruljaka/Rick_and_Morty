package com.ruslangrigoriev.rickandmorty.episodes.presentation.list.adapters

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.ruslangrigoriev.rickandmorty.core.data.dto.episode_dto.Episode
import com.ruslangrigoriev.rickandmorty.databinding.ItemEpisodeBinding

class EpisodeViewHolder(
    private val view: View,
    private val onItemClicked: (id: Int) -> Unit,
) : RecyclerView.ViewHolder(view) {

    fun bind(episode: Episode) {
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