package com.ruslangrigoriev.rickandmorty.di

import com.ruslangrigoriev.rickandmorty.presentation.ui.characterDetails.CharacterDetailsFragment
import com.ruslangrigoriev.rickandmorty.presentation.ui.characters.CharactersFragment
import com.ruslangrigoriev.rickandmorty.presentation.ui.episodeDetails.EpisodeDetailsFragment
import com.ruslangrigoriev.rickandmorty.presentation.ui.episodes.EpisodesFragment
import com.ruslangrigoriev.rickandmorty.presentation.ui.locationDetails.LocationDetailsFragment
import com.ruslangrigoriev.rickandmorty.presentation.ui.locations.LocationsFragment
import com.ruslangrigoriev.rickandmorty.presentation.ui.main.MainActivity
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(
    modules = [AppModule::class,
        NetworkModule::class,
        RoomModule::class,
        RepositoryModule::class,
        MapperModule::class]
)
interface AppComponent {
    fun inject(mainActivity: MainActivity)
    fun inject(charactersFragment: CharactersFragment)
    fun inject(charactersDetailsFragment: CharacterDetailsFragment)
    fun inject(locationsFragment: LocationsFragment)
    fun inject(locationDetailsFragment: LocationDetailsFragment)
    fun inject(episodesFragment: EpisodesFragment)
    fun inject(episodeDetailsFragment: EpisodeDetailsFragment)
}