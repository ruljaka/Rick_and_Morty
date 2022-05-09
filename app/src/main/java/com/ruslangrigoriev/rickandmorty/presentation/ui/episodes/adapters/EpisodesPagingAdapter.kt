package com.ruslangrigoriev.rickandmorty.presentation.ui.episodes.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import com.ruslangrigoriev.rickandmorty.R
import com.ruslangrigoriev.rickandmorty.data.dto.episodeDTO.EpisodeDTO

class EpisodesPagingAdapter(
    private val onItemClicked: (id: Int) -> Unit,
) : PagingDataAdapter<EpisodeDTO, EpisodeViewHolder>(diffCallback) {

    companion object {
        val diffCallback = object : DiffUtil.ItemCallback<EpisodeDTO>() {
            override fun areItemsTheSame(oldItem: EpisodeDTO, newItem: EpisodeDTO): Boolean {
                return oldItem.id == newItem.id
            }

            override fun areContentsTheSame(oldItem: EpisodeDTO, newItem: EpisodeDTO): Boolean {
                return oldItem == newItem
            }
        }
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): EpisodeViewHolder {
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.item_episode, parent, false)
        return EpisodeViewHolder(view, onItemClicked)
    }

    override fun onBindViewHolder(holder: EpisodeViewHolder, position: Int) {
        getItem(position)?.let { holder.bind(it) }
    }

}