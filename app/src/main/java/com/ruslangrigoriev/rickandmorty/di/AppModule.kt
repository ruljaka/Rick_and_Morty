package com.ruslangrigoriev.rickandmorty.di

import android.content.Context
import com.ruslangrigoriev.rickandmorty.data.local.CharactersDao
import com.ruslangrigoriev.rickandmorty.data.local.EpisodesDao
import com.ruslangrigoriev.rickandmorty.data.local.LocationsDao
import com.ruslangrigoriev.rickandmorty.data.remote.ApiService
import com.ruslangrigoriev.rickandmorty.data.repository.CharactersRepositoryImpl
import com.ruslangrigoriev.rickandmorty.data.repository.EpisodesRepositoryImpl
import com.ruslangrigoriev.rickandmorty.data.repository.LocationsRepositoryImpl
import com.ruslangrigoriev.rickandmorty.domain.repository.CharactersRepository
import com.ruslangrigoriev.rickandmorty.domain.repository.EpisodesRepository
import com.ruslangrigoriev.rickandmorty.domain.repository.LocationsRepository
import com.ruslangrigoriev.rickandmorty.presentation.common.FragmentNavigator
import com.ruslangrigoriev.rickandmorty.presentation.network.NetworkStatusTracker
import dagger.Module
import dagger.Provides
import javax.inject.Singleton


@Module
class AppModule(private val context: Context) {

    @Singleton
    @Provides
    fun provideContext(): Context {
        return context
    }

    @Singleton
    @Provides
    fun provideCharactersRepository(
        apiService: ApiService,
        charactersDao: CharactersDao,
        episodesDao: EpisodesDao
    ): CharactersRepository {
        return CharactersRepositoryImpl(apiService, charactersDao, episodesDao)
    }

    @Singleton
    @Provides
    fun provideEpisodesRepository(
        apiService: ApiService,
        charactersDao: CharactersDao,
        episodesDao: EpisodesDao
    ): EpisodesRepository {
        return EpisodesRepositoryImpl(apiService, charactersDao, episodesDao)
    }

    @Singleton
    @Provides
    fun provideLocationsRepository(
        apiService: ApiService,
        charactersDao: CharactersDao,
        locationsDao: LocationsDao
    ): LocationsRepository {
        return LocationsRepositoryImpl(apiService, charactersDao, locationsDao)
    }

    @Singleton
    @Provides
    fun provideFragmentNavigator(): FragmentNavigator {
        return FragmentNavigator()
    }

    @Singleton
    @Provides
    fun provideNetworkStatusTracker(context: Context): NetworkStatusTracker {
        return NetworkStatusTracker(context)
    }

}