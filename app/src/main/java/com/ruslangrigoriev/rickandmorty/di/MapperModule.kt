package com.ruslangrigoriev.rickandmorty.di

import com.ruslangrigoriev.rickandmorty.presentation.mappers.CharacterMapper
import com.ruslangrigoriev.rickandmorty.presentation.mappers.EpisodeMapper
import com.ruslangrigoriev.rickandmorty.presentation.mappers.LocationMapper
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class MapperModule {

    @Singleton
    @Provides
    fun provideCharacterMapper(): CharacterMapper {
        return CharacterMapper()
    }

    @Singleton
    @Provides
    fun provideLocationMapper(): LocationMapper {
        return LocationMapper()
    }

    @Singleton
    @Provides
    fun provideEpisodeMapper(): EpisodeMapper {
        return EpisodeMapper()
    }
}