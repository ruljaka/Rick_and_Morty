package com.ruslangrigoriev.rickandmorty.episodes.presentation.details.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.ruslangrigoriev.rickandmorty.R
import com.ruslangrigoriev.rickandmorty.characters.presentation.list.adapters.CharacterViewHolder
import com.ruslangrigoriev.rickandmorty.core.data.dto.character_dto.Character

class CharactersAdapter(
    private val dataList: List<Character>,
    private val onItemClicked: (id: Int) -> Unit
) : RecyclerView.Adapter<CharacterViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CharacterViewHolder {
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.item_character, parent, false)
        return CharacterViewHolder(view, onItemClicked)
    }

    override fun onBindViewHolder(holder: CharacterViewHolder, position: Int) {
        holder.bind(dataList[position])
    }

    override fun getItemCount(): Int = dataList.size
}