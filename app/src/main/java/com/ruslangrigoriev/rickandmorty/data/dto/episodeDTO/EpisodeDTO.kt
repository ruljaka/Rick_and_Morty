package com.ruslangrigoriev.rickandmorty.data.dto.episodeDTO


import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

@Entity
data class EpisodeDTO(
    @PrimaryKey
    @SerializedName("id")
    val id: Int,
    @SerializedName("name")
    val name: String,
    @SerializedName("episode")
    val episode: String,
    @SerializedName("air_date")
    val airDate: String,
    @SerializedName("characters")
    val characters: List<String>,
    @SerializedName("created")
    val created: String,
    @SerializedName("url")
    val url: String
)