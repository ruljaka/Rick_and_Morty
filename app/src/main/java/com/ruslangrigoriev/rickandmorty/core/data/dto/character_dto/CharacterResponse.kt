package com.ruslangrigoriev.rickandmorty.core.data.dto.character_dto


import com.google.gson.annotations.SerializedName

data class CharacterResponse(
    @SerializedName("info")
    val info: Info,
    @SerializedName("results")
    val characters: List<Character>
)