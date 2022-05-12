package com.ruslangrigoriev.rickandmorty.data.dto_and_entity.locationDTO


import com.google.gson.annotations.SerializedName

data class LocationResponse(
    @SerializedName("info")
    val info: Info,
    @SerializedName("results")
    val locations: List<LocationDTO>
)