package com.ruslangrigoriev.rickandmorty.di

import android.content.Context
import com.ruslangrigoriev.rickandmorty.characters.presentation.details.CharacterDetailsFragment
import com.ruslangrigoriev.rickandmorty.characters.presentation.list.CharactersFragment
import com.ruslangrigoriev.rickandmorty.episodes.presentation.details.EpisodeDetailsFragment
import com.ruslangrigoriev.rickandmorty.episodes.presentation.list.EpisodesFragment
import com.ruslangrigoriev.rickandmorty.locations.presentation.details.LocationDetailsFragment
import com.ruslangrigoriev.rickandmorty.locations.presentation.list.LocationsFragment
import com.ruslangrigoriev.rickandmorty.core.presentation.main.MainActivity
import dagger.BindsInstance
import dagger.Component

@AppScope
@Component(modules = [AppModule::class])
interface AppComponent {

    /*
    Вариант с билдером:
    @Component.Builder
    interface Builder {

        fun build(): AppComponent

        @BindsInstance
        fun context(context: Context): Builder
    }*/


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