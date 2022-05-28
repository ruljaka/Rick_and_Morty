package com.ruslangrigoriev.rickandmorty.core.data

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.ruslangrigoriev.rickandmorty.core.data.dto.character_dto.Character
import com.ruslangrigoriev.rickandmorty.core.data.dto.episode_dto.Episode
import com.ruslangrigoriev.rickandmorty.core.data.dto.location_dto.Location
import com.ruslangrigoriev.rickandmorty.core.data.coverters.ListConverter
import com.ruslangrigoriev.rickandmorty.characters.data.local.CharactersDao
import com.ruslangrigoriev.rickandmorty.episodes.data.local.EpisodesDao
import com.ruslangrigoriev.rickandmorty.locations.data.local.LocationsDao
import com.ruslangrigoriev.rickandmorty.core.data.coverters.LocationConverter
import com.ruslangrigoriev.rickandmorty.core.data.coverters.OriginConverter

@Database(
    entities = [Character::class, Episode::class, Location::class],
    version = 1,
    exportSchema = false
)
@TypeConverters(ListConverter::class, LocationConverter::class, OriginConverter::class)
abstract class AppDataBase : RoomDatabase() {
    abstract fun getCharactersDao(): CharactersDao
    abstract fun getEpisodesDao(): EpisodesDao
    abstract fun getLocationsDao(): LocationsDao
}