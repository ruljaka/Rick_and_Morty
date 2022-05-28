package com.ruslangrigoriev.rickandmorty.core.data.dto.location_dto


import com.google.gson.annotations.SerializedName

data class LocationResponse(
    @SerializedName("info")
    val info: Info,
    @SerializedName("results")
    val locations: List<Location>
)