package com.ruslangrigoriev.rickandmorty.di

import android.content.Context
import com.ruslangrigoriev.rickandmorty.data.local.CharactersDao
import com.ruslangrigoriev.rickandmorty.data.local.EpisodesDao
import com.ruslangrigoriev.rickandmorty.data.remote.ApiService
import com.ruslangrigoriev.rickandmorty.data.repository.CharacterRepositoryImpl
import com.ruslangrigoriev.rickandmorty.domain.repository.CharacterRepository
import com.ruslangrigoriev.rickandmorty.presentation.FragmentNavigator
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
    fun provideCharacterRepository(
        apiService: ApiService,
        charactersDao: CharactersDao,
        episodesDao: EpisodesDao
    ): CharacterRepository {
        return CharacterRepositoryImpl(apiService, charactersDao,episodesDao)
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