package com.ruslangrigoriev.rickandmorty.data.dto.episodeDTO


import com.google.gson.annotations.SerializedName

data class EpisodeResponse(
    @SerializedName("info")
    val info: Info,
    @SerializedName("results")
    val episodes: List<EpisodeDTO>
)