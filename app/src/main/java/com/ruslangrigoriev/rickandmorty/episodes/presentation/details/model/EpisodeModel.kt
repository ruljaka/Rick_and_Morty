package com.ruslangrigoriev.rickandmorty.episodes.presentation.details.model

import com.ruslangrigoriev.rickandmorty.core.data.dto.character_dto.Character

data class EpisodeModel(
    val id: Int,
    val name: String,
    val episode: String,
    val airDate: String,
    val characters: List<Character>
)