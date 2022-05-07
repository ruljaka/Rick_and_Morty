package com.ruslangrigoriev.rickandmorty.di

import com.ruslangrigoriev.rickandmorty.data.remote.CharactersService
import com.ruslangrigoriev.rickandmorty.data.remote.EpisodesService
import com.ruslangrigoriev.rickandmorty.data.remote.LocationsService
import dagger.Module
import dagger.Provides
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
class NetworkModule {

    companion object {
        private const val BASE_URL = "https://rickandmortyapi.com/api/"
    }

    @Singleton
    @Provides
    fun provideRetrofit(): Retrofit {
        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    @Singleton
    @Provides
    fun provideCharactersService(retrofit: Retrofit): CharactersService {
        return retrofit.create(CharactersService::class.java)
    }

    @Singleton
    @Provides
    fun provideEpisodesService(retrofit: Retrofit): EpisodesService {
        return retrofit.create(EpisodesService::class.java)
    }

    @Singleton
    @Provides
    fun provideLocationsService(retrofit: Retrofit): LocationsService {
        return retrofit.create(LocationsService::class.java)
    }

}