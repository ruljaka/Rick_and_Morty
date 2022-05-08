package com.ruslangrigoriev.rickandmorty.di

import android.content.Context
import androidx.room.Room
import com.ruslangrigoriev.rickandmorty.data.local.AppDataBase
import com.ruslangrigoriev.rickandmorty.data.local.CharactersDao
import com.ruslangrigoriev.rickandmorty.data.local.EpisodesDao
import com.ruslangrigoriev.rickandmorty.data.local.LocationsDao
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class RoomModule {

    @Singleton
    @Provides
    fun provideAppDataBase(appContext: Context): AppDataBase {
        return Room.databaseBuilder(
            appContext,
            AppDataBase::class.java,
            "app_db"
        )
            .fallbackToDestructiveMigration()
            .build()
    }

    @Singleton
    @Provides
    fun provideCharactersDao(appDataBase: AppDataBase): CharactersDao {
        return appDataBase.getCharactersDao()
    }

    @Singleton
    @Provides
    fun provideEpisodesDao(appDataBase: AppDataBase): EpisodesDao {
        return appDataBase.getEpisodesDao()
    }

    @Singleton
    @Provides
    fun provideLocationsDao(appDataBase: AppDataBase): LocationsDao {
        return appDataBase.getLocationsDao()
    }
    
}