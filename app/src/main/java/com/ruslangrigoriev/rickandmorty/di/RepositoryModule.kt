package com.ruslangrigoriev.rickandmorty.di

import com.ruslangrigoriev.rickandmorty.data.repository.CharactersRepositoryImpl
import com.ruslangrigoriev.rickandmorty.data.repository.EpisodesRepositoryImpl
import com.ruslangrigoriev.rickandmorty.data.repository.LocationsRepositoryImpl
import com.ruslangrigoriev.rickandmorty.domain.repository.CharactersRepository
import com.ruslangrigoriev.rickandmorty.domain.repository.EpisodesRepository
import com.ruslangrigoriev.rickandmorty.domain.repository.LocationsRepository
import dagger.Binds
import dagger.Module

@Module
interface RepositoryModule {

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