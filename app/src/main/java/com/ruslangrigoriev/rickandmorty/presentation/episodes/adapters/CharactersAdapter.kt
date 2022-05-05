package com.ruslangrigoriev.rickandmorty.presentation.episodes.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.ruslangrigoriev.rickandmorty.R
import com.ruslangrigoriev.rickandmorty.data.dto.characterDTO.CharacterDTO
import com.ruslangrigoriev.rickandmorty.data.dto.episodeDTO.EpisodeDTO
import com.ruslangrigoriev.rickandmorty.presentation.characters.adapters.CharacterViewHolder
import com.ruslangrigoriev.rickandmorty.presentation.episodes.adapters.EpisodeViewHolder

class CharactersAdapter(
    private val onItemClicked: (id: Int) -> Unit
) : RecyclerView.Adapter<CharacterViewHolder>() {

    private var dataList: List<CharacterDTO>? = null

    fun setData(list: List<CharacterDTO>) {
        dataList = list
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CharacterViewHolder {
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.item_character, parent, false)
        return CharacterViewHolder(view, onItemClicked)
    }

    override fun onBindViewHolder(holder: CharacterViewHolder, position: Int) {
        dataList?.get(position)?.let { holder.bind(it) }
    }

    override fun getItemCount(): Int = dataList?.size ?: 0

}