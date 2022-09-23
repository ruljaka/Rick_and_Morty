package com.ruslangrigoriev.rickandmorty.presentation.model

import com.ruslangrigoriev.rickandmorty.domain.entity.characters.Character

data class LocationModel(
    val id: Int,
    val name: String,
    val type: String,
    val dimension: String,
    val residents: List<Character>,
)