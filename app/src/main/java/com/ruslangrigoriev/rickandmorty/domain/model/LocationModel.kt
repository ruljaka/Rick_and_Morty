package com.ruslangrigoriev.rickandmorty.domain.model

import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName
import com.ruslangrigoriev.rickandmorty.data.dto.characterDTO.CharacterDTO

data class LocationModel(
    val id: Int,
    val name: String,
    val type: String,
    val dimension: String,
    val residents: List<CharacterDTO>,
)