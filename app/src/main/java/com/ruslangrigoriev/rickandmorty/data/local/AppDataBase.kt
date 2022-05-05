package com.ruslangrigoriev.rickandmorty.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.ruslangrigoriev.rickandmorty.data.dto.characterDTO.CharacterDTO
import com.ruslangrigoriev.rickandmorty.data.dto.episodeDTO.EpisodeDTO
import com.ruslangrigoriev.rickandmorty.data.local.coverters.ListConverter
import com.ruslangrigoriev.rickandmorty.data.local.coverters.LocationConverter
import com.ruslangrigoriev.rickandmorty.data.local.coverters.OriginConverter

@Database(entities = [CharacterDTO::class, EpisodeDTO:: class ], version = 1, exportSchema = false)
@TypeConverters(ListConverter::class, LocationConverter::class, OriginConverter::class)
abstract class AppDataBase : RoomDatabase() {
    abstract fun getCharactersDao(): CharactersDao
    abstract fun getEpisodesDao(): EpisodesDao
}