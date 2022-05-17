package com.ruslangrigoriev.rickandmorty.presentation.model

import com.ruslangrigoriev.rickandmorty.data.dto_and_entity.characters.Character

data class EpisodeModel(
    val id: Int,
    val name: String,
    val episode: String,
    val airDate: String,
    val characters: List<Character>
)