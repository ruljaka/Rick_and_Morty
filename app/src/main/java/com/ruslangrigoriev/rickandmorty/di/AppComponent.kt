package com.ruslangrigoriev.rickandmorty.di

import com.ruslangrigoriev.rickandmorty.presentation.main.MainActivity
import com.ruslangrigoriev.rickandmorty.presentation.characterDetails.CharacterDetailsFragment
import com.ruslangrigoriev.rickandmorty.presentation.characters.CharactersFragment
import com.ruslangrigoriev.rickandmorty.presentation.episodeDetails.EpisodeDetailsFragment
import com.ruslangrigoriev.rickandmorty.presentation.episodes.EpisodesFragment
import com.ruslangrigoriev.rickandmorty.presentation.locationDetails.LocationDetailsFragment
import com.ruslangrigoriev.rickandmorty.presentation.locations.LocationsFragment
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = [AppModule::class, NetworkModule::class, RoomModule::class])
interface AppComponent {

    fun inject(mainActivity: MainActivity)
    fun inject(charactersFragment: CharactersFragment)
    fun inject(charactersDetailsFragment: CharacterDetailsFragment)
    fun inject(locationsFragment: LocationsFragment)
    fun inject(locationDetailsFragment: LocationDetailsFragment)
    fun inject(episodesFragment: EpisodesFragment)
    fun inject(episodeDetailsFragment: EpisodeDetailsFragment)
}