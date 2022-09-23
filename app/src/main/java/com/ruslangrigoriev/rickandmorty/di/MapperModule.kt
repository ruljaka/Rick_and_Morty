package com.ruslangrigoriev.rickandmorty.di

import com.ruslangrigoriev.rickandmorty.domain.entity.characters.Character
import com.ruslangrigoriev.rickandmorty.domain.entity.episodes.Episode
import com.ruslangrigoriev.rickandmorty.domain.entity.locations.Location
import com.ruslangrigoriev.rickandmorty.presentation.mappers.CharacterMapper
import com.ruslangrigoriev.rickandmorty.presentation.mappers.EpisodeMapper
import com.ruslangrigoriev.rickandmorty.presentation.mappers.LocationMapper
import com.ruslangrigoriev.rickandmorty.presentation.mappers.Mapper
import com.ruslangrigoriev.rickandmorty.presentation.model.CharacterModel
import com.ruslangrigoriev.rickandmorty.presentation.model.EpisodeModel
import com.ruslangrigoriev.rickandmorty.presentation.model.LocationModel
import dagger.Binds
import dagger.Module

@Module
abstract class MapperModule {

    @Binds
    @AppScope
    abstract fun provideCharacterMapper(characterMapper: CharacterMapper):
            Mapper<@JvmSuppressWildcards Character, List<@JvmSuppressWildcards Episode>, @JvmSuppressWildcards CharacterModel>

    @Binds
    @AppScope
    abstract fun provideEpisodeMapper(episodeMapper: EpisodeMapper):
            Mapper<@JvmSuppressWildcards Episode, List<@JvmSuppressWildcards Character>, @JvmSuppressWildcards EpisodeModel>

    @Binds
    @AppScope
    abstract fun provideLocationMapper(locationMapper: LocationMapper):
            Mapper<@JvmSuppressWildcards Location, List<@JvmSuppressWildcards Character>, @JvmSuppressWildcards LocationModel>

}