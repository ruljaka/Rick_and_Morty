package com.ruslangrigoriev.rickandmorty.presentation.characters.adapters

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

    fun bind(character: CharacterDTO) {
        val binding = ItemCharacterBinding.bind(view)
        with(binding) {
            root.setOnClickListener {
                onItemClicked(character.id)
            }
            characterNameTextView.text = character.name
            characterSpeciesTextView.text = character.species
            characterGenderTextView.text = character.gender
            characterImageView.load(character.image) {
                placeholder(R.drawable.placeholder)
                error(R.drawable.placeholder)
            }
            when (character.status) {
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