package com.ruslangrigoriev.rickandmorty.presentation.episodes

import java.io.Serializable

data class EpisodesFilter(
    val name: String? = null,
    val episode: String? = null,
) : Serializable{
}