package com.ruslangrigoriev.rickandmorty.di

import android.content.Context
import com.ruslangrigoriev.rickandmorty.presentation.mappers.CharacterMapper
import com.ruslangrigoriev.rickandmorty.presentation.mappers.EpisodeMapper
import com.ruslangrigoriev.rickandmorty.presentation.mappers.LocationMapper
import com.ruslangrigoriev.rickandmorty.presentation.networkTracker.NetworkStatusTracker
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
    fun provideNetworkStatusTracker(context: Context): NetworkStatusTracker {
        return NetworkStatusTracker(context)
    }

}