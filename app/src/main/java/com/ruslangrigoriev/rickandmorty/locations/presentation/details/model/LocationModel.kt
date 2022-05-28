package com.ruslangrigoriev.rickandmorty.locations.presentation.details.model

import com.ruslangrigoriev.rickandmorty.core.data.dto.character_dto.Character

data class LocationModel(
    val id: Int,
    val name: String,
    val type: String,
    val dimension: String,
    val residents: List<Character>,
)