package com.ruslangrigoriev.rickandmorty.locations.presentation.list.filter

import java.io.Serializable

data class LocationsFilter(
    val name: String? = null,
    val type: String? = null,
    val dimension: String? = null,
) : Serializable