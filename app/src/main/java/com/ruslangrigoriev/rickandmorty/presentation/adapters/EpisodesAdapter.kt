package com.ruslangrigoriev.rickandmorty.presentation.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.ruslangrigoriev.rickandmorty.R
import com.ruslangrigoriev.rickandmorty.data.dto.episodeDTO.EpisodeDTO

class EpisodesAdapter(
    private val onItemClicked: (id: Int) -> Unit
) : RecyclerView.Adapter<EpisodeViewHolder>() {

    private var dataList: List<EpisodeDTO>? = null

    fun setData(list: List<EpisodeDTO>) {
        dataList = list
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): EpisodeViewHolder {
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.item_episode, parent, false)
        return EpisodeViewHolder(view, onItemClicked)
    }

    override fun onBindViewHolder(holder: EpisodeViewHolder, position: Int) {
        dataList?.get(position)?.let { holder.bind(it) }
    }

    override fun getItemCount(): Int = dataList?.size ?: 0

}