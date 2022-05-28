package com.ruslangrigoriev.rickandmorty.di

import com.ruslangrigoriev.rickandmorty.characters.data.repository.CharactersRepositoryImpl
import com.ruslangrigoriev.rickandmorty.episodes.data.repository.EpisodesRepositoryImpl
import com.ruslangrigoriev.rickandmorty.locations.data.repository.LocationsRepositoryImpl
import com.ruslangrigoriev.rickandmorty.characters.domain.CharactersRepository
import com.ruslangrigoriev.rickandmorty.episodes.domain.EpisodesRepository
import com.ruslangrigoriev.rickandmorty.locations.domain.LocationsRepository
import dagger.Binds
import dagger.Module

@Module
interface AppBindModule {

    @AppScope
    @Binds
    fun bindCharacterRepository(charactersRepositoryImpl: CharactersRepositoryImpl): CharactersRepository

    @AppScope
    @Binds
    fun bindEpisodeRepository(episodesRepositoryImpl: EpisodesRepositoryImpl): EpisodesRepository

    @AppScope
    @Binds
    fun bindLocationRepository(locationRepositoryImpl: LocationsRepositoryImpl): LocationsRepository
}