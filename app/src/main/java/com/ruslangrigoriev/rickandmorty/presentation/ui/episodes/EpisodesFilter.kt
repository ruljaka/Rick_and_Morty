package com.ruslangrigoriev.rickandmorty.presentation.ui.episodes

import java.io.Serializable

data class EpisodesFilter(
    val name: String? = null,
    val episode: String? = null,
) : Serializable