package com.ruslangrigoriev.rickandmorty.presentation.characters

import java.io.Serializable

data class CharactersFilter(
    val name: String? = null,
    val status: String? = null,
    val species: String? = null,
    val type: String? = null,
    val gender: String? = null
) : Serializable