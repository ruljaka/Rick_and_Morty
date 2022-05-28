package com.ruslangrigoriev.rickandmorty.core.data.dto.character_dto


import com.google.gson.annotations.SerializedName

data class Origin(
    @SerializedName("name")
    val name: String,
    @SerializedName("url")
    val url: String
)