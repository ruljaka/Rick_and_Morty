package com.ruslangrigoriev.rickandmorty.presentation.model

import com.ruslangrigoriev.rickandmorty.data.dto.episodeDTO.EpisodeDTO

data class CharacterModel(
    val id: Int,
    val status: String,
    val name: String,
    val species: String,
    val gender: String,
    val type: String,
    val image: String,
    val episodes: List<EpisodeDTO>,
    val locationName: String,
    val locationID: Int?,
    val originName: String,
    val originID: Int?,
)
