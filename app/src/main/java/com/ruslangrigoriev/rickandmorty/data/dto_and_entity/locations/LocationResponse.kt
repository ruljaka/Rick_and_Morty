package com.ruslangrigoriev.rickandmorty.data.dto_and_entity.locations


import com.google.gson.annotations.SerializedName

data class LocationResponse(
    @SerializedName("info")
    val info: Info,
    @SerializedName("results")
    val locations: List<Location>
)