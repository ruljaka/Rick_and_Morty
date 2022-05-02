package com.ruslangrigoriev.rickandmorty.presentation

interface FragmentNavigator {
    fun toCharacters()
    fun toCharacterDetails()
    fun toLocations()
    fun toLocationDetails()
    fun toEpisodes()
    fun toEpisodeDetails()
}