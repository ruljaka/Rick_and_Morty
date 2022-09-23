package com.ruslangrigoriev.rickandmorty.di

import android.content.Context
import com.ruslangrigoriev.rickandmorty.presentation.ui.characterDetails.CharacterDetailsFragment
import com.ruslangrigoriev.rickandmorty.presentation.ui.characters.CharactersFragment
import com.ruslangrigoriev.rickandmorty.presentation.ui.episodeDetails.EpisodeDetailsFragment
import com.ruslangrigoriev.rickandmorty.presentation.ui.episodes.EpisodesFragment
import com.ruslangrigoriev.rickandmorty.presentation.ui.locationDetails.LocationDetailsFragment
import com.ruslangrigoriev.rickandmorty.presentation.ui.locations.LocationsFragment
import com.ruslangrigoriev.rickandmorty.presentation.ui.main.MainActivity
import dagger.BindsInstance
import dagger.Component

@AppScope
@Component(modules = [AppModule::class])
interface AppComponent {

    @Component.Factory
    interface Factory {
        fun create(@BindsInstance context: Context): AppComponent
    }

    fun inject(mainActivity: MainActivity)
    fun inject(charactersFragment: CharactersFragment)
    fun inject(charactersDetailsFragment: CharacterDetailsFragment)
    fun inject(locationsFragment: LocationsFragment)
    fun inject(locationDetailsFragment: LocationDetailsFragment)
    fun inject(episodesFragment: EpisodesFragment)
    fun inject(episodeDetailsFragment: EpisodeDetailsFragment)
}