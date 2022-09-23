package com.ruslangrigoriev.rickandmorty.data.source.local

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.ruslangrigoriev.rickandmorty.domain.entity.characters.Character
import com.ruslangrigoriev.rickandmorty.domain.entity.episodes.Episode
import com.ruslangrigoriev.rickandmorty.domain.entity.locations.Location
import com.ruslangrigoriev.rickandmorty.data.source.local.coverters.ListConverter
import com.ruslangrigoriev.rickandmorty.data.source.local.coverters.LocationConverter
import com.ruslangrigoriev.rickandmorty.data.source.local.coverters.OriginConverter

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