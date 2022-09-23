package com.ruslangrigoriev.rickandmorty.di

import com.ruslangrigoriev.rickandmorty.data.source.remote.CharactersService
import com.ruslangrigoriev.rickandmorty.data.source.remote.EpisodesService
import com.ruslangrigoriev.rickandmorty.data.source.remote.LocationsService
import dagger.Module
import dagger.Provides
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

@Module
class NetworkModule {

    companion object {
        private const val BASE_URL = "https://rickandmortyapi.com/api/"
    }

    @AppScope
    @Provides
    fun provideRetrofit(): Retrofit {
        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    @AppScope
    @Provides
    fun provideCharactersService(retrofit: Retrofit): CharactersService {
        return retrofit.create(CharactersService::class.java)
    }

    @AppScope
    @Provides
    fun provideEpisodesService(retrofit: Retrofit): EpisodesService {
        return retrofit.create(EpisodesService::class.java)
    }

    @AppScope
    @Provides
    fun provideLocationsService(retrofit: Retrofit): LocationsService {
        return retrofit.create(LocationsService::class.java)
    }

}