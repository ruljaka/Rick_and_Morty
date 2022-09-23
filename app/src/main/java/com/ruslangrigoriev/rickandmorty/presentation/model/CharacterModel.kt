package com.ruslangrigoriev.rickandmorty.presentation.model

import com.ruslangrigoriev.rickandmorty.domain.entity.episodes.Episode

data class CharacterModel(
    val id: Int,
    val status: String,
    val name: String,
    val species: String,
    val gender: String,
    val type: String,
    val image: String,
    val episodes: List<Episode>,
    val locationName: String,
    val locationID: Int?,
    val originName: String,
    val originID: Int?,
)
