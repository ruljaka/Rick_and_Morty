package com.ruslangrigoriev.rickandmorty.di

import android.content.Context
import androidx.room.Room
import com.ruslangrigoriev.rickandmorty.core.data.AppDataBase
import com.ruslangrigoriev.rickandmorty.characters.data.local.CharactersDao
import com.ruslangrigoriev.rickandmorty.episodes.data.local.EpisodesDao
import com.ruslangrigoriev.rickandmorty.locations.data.local.LocationsDao
import com.ruslangrigoriev.rickandmorty.core.data.coverters.ListConverter
import com.ruslangrigoriev.rickandmorty.core.data.coverters.LocationConverter
import com.ruslangrigoriev.rickandmorty.core.data.coverters.OriginConverter
import dagger.Module
import dagger.Provides

@Module
class RoomModule {

    @AppScope
    @Provides
    fun provideAppDataBase(appContext: Context): AppDataBase {
        return Room.databaseBuilder(
            appContext,
            AppDataBase::class.java,
            "app_db"
        )
            .fallbackToDestructiveMigration()
            .addTypeConverter(ListConverter())
            .addTypeConverter(LocationConverter())
            .addTypeConverter(OriginConverter())
            .build()
    }

    @AppScope
    @Provides
    fun provideCharactersDao(appDataBase: AppDataBase): CharactersDao {
        return appDataBase.getCharactersDao()
    }

    @AppScope
    @Provides
    fun provideEpisodesDao(appDataBase: AppDataBase): EpisodesDao {
        return appDataBase.getEpisodesDao()
    }

    @AppScope
    @Provides
    fun provideLocationsDao(appDataBase: AppDataBase): LocationsDao {
        return appDataBase.getLocationsDao()
    }
    
}