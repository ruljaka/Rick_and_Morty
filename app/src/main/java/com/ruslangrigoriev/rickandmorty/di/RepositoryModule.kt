package com.ruslangrigoriev.rickandmorty.di

import com.ruslangrigoriev.rickandmorty.data.local.CharactersDao
import com.ruslangrigoriev.rickandmorty.data.local.EpisodesDao
import com.ruslangrigoriev.rickandmorty.data.local.LocationsDao
import com.ruslangrigoriev.rickandmorty.data.remote.CharactersService
import com.ruslangrigoriev.rickandmorty.data.remote.EpisodesService
import com.ruslangrigoriev.rickandmorty.data.remote.LocationsService
import com.ruslangrigoriev.rickandmorty.data.repository.CharactersRepositoryImpl
import com.ruslangrigoriev.rickandmorty.data.repository.EpisodesRepositoryImpl
import com.ruslangrigoriev.rickandmorty.data.repository.LocationsRepositoryImpl
import com.ruslangrigoriev.rickandmorty.domain.repository.CharactersRepository
import com.ruslangrigoriev.rickandmorty.domain.repository.EpisodesRepository
import com.ruslangrigoriev.rickandmorty.domain.repository.LocationsRepository
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class RepositoryModule {

    @Singleton
    @Provides
    fun provideCharactersRepository(
        charactersService: CharactersService,
        charactersDao: CharactersDao,
        episodesDao: EpisodesDao
    ): CharactersRepository {
        return CharactersRepositoryImpl(charactersService, charactersDao, episodesDao)
    }

    @Singleton
    @Provides
    fun provideEpisodesRepository(
        episodesService: EpisodesService,
        charactersDao: CharactersDao,
        episodesDao: EpisodesDao
    ): EpisodesRepository {
        return EpisodesRepositoryImpl(episodesService, charactersDao, episodesDao)
    }

    @Singleton
    @Provides
    fun provideLocationsRepository(
        locationsService: LocationsService,
        charactersDao: CharactersDao,
        locationsDao: LocationsDao
    ): LocationsRepository {
        return LocationsRepositoryImpl(locationsService, charactersDao, locationsDao)
    }
}