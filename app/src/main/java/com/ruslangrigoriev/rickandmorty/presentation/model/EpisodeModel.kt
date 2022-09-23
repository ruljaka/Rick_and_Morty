package com.ruslangrigoriev.rickandmorty.presentation.model

import com.ruslangrigoriev.rickandmorty.domain.entity.characters.Character

data class EpisodeModel(
    val id: Int,
    val name: String,
    val episode: String,
    val airDate: String,
    val characters: List<Character>
)