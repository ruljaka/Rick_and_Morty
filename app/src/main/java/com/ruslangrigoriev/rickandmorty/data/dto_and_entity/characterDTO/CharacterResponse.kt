package com.ruslangrigoriev.rickandmorty.data.dto_and_entity.characterDTO


import com.google.gson.annotations.SerializedName

data class CharacterResponse(
    @SerializedName("info")
    val info: Info,
    @SerializedName("results")
    val characters: List<CharacterDTO>
)