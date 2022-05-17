package com.ruslangrigoriev.rickandmorty.data.dto_and_entity.characters


import com.google.gson.annotations.SerializedName

data class Origin(
    @SerializedName("name")
    val name: String,
    @SerializedName("url")
    val url: String
)