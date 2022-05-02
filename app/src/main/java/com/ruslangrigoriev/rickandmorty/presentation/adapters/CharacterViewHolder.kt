package com.ruslangrigoriev.rickandmorty.presentation.adapters

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.ruslangrigoriev.rickandmorty.R
import com.ruslangrigoriev.rickandmorty.data.dto.characterDTO.CharacterDTO
import com.ruslangrigoriev.rickandmorty.databinding.ItemCharacterBinding

class CharacterViewHolder(
    private val view: View,
    private val onItemClicked: (id: Int) -> Unit,
) : RecyclerView.ViewHolder(view) {

    fun bind(characterDTO: CharacterDTO) {
        val binding = ItemCharacterBinding.bind(view)
        with(binding) {
            root.setOnClickListener {
                onItemClicked(characterDTO.id)
            }
            characterNameTextView.text = characterDTO.name
            characterSpeciesTextView.text = characterDTO.species
            characterGenderTextView.text = characterDTO.gender
            characterImageView.load(characterDTO.image)
            when (characterDTO.status) {
                "Alive" -> {
                    characterStatusImageView.setImageResource(R.drawable.icon_status_alive)
                }
                "Dead" -> {
                    characterStatusImageView.setImageResource(R.drawable.icon_status_dead)
                }
                else -> {
                    characterStatusImageView.setImageResource(R.drawable.icon_status_unknown)
                }
            }

        }
    }
}