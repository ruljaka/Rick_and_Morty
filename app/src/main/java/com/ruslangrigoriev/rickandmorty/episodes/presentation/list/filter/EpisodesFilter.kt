package com.ruslangrigoriev.rickandmorty.episodes.presentation.list.filter

import java.io.Serializable

data class EpisodesFilter(
    val name: String? = null,
    val episode: String? = null,
) : Serializable