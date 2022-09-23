package com.ruslangrigoriev.rickandmorty.domain.entity.locations


import com.google.gson.annotations.SerializedName

data class LocationResponse(
    @SerializedName("info")
    val info: Info,
    @SerializedName("results")
    val locations: List<Location>
)