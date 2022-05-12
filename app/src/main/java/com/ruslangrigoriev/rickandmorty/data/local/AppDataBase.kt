package com.ruslangrigoriev.rickandmorty.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.ruslangrigoriev.rickandmorty.data.dto_and_entity.characterDTO.CharacterDTO
import com.ruslangrigoriev.rickandmorty.data.dto_and_entity.episodeDTO.EpisodeDTO
import com.ruslangrigoriev.rickandmorty.data.dto_and_entity.locationDTO.LocationDTO
import com.ruslangrigoriev.rickandmorty.data.local.coverters.ListConverter
import com.ruslangrigoriev.rickandmorty.data.local.coverters.LocationConverter
import com.ruslangrigoriev.rickandmorty.data.local.coverters.OriginConverter

@Database(
    entities = [CharacterDTO::class, EpisodeDTO::class, LocationDTO::class],
    version = 1,
    exportSchema = false
)
@TypeConverters(ListConverter::class, LocationConverter::class, OriginConverter::class)
abstract class AppDataBase : RoomDatabase() {
    abstract fun getCharactersDao(): CharactersDao
    abstract fun getEpisodesDao(): EpisodesDao
    abstract fun getLocationsDao(): LocationsDao
}