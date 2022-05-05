package com.ruslangrigoriev.rickandmorty.domain.model

import com.ruslangrigoriev.rickandmorty.data.dto.characterDTO.CharacterDTO

data class EpisodeModel(
    val id: Int,
    val name: String,
    val episode: String,
    val airDate: String,
    val characters: List<CharacterDTO>
)