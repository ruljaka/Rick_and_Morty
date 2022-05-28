package com.ruslangrigoriev.rickandmorty.characters.presentation.details.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.ruslangrigoriev.rickandmorty.R
import com.ruslangrigoriev.rickandmorty.core.data.dto.episode_dto.Episode
import com.ruslangrigoriev.rickandmorty.episodes.presentation.list.adapters.EpisodeViewHolder

class EpisodesAdapter(
    private val dataList: List<Episode>,
    private val onItemClicked: (id: Int) -> Unit
) : RecyclerView.Adapter<EpisodeViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): EpisodeViewHolder {
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.item_episode, parent, false)
        return EpisodeViewHolder(view, onItemClicked)
    }

    override fun onBindViewHolder(holder: EpisodeViewHolder, position: Int) {
        holder.bind(dataList[position])
    }

    override fun getItemCount(): Int = dataList.size
}