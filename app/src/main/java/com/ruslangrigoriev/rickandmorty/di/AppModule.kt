package com.ruslangrigoriev.rickandmorty.di

import dagger.Module
import dagger.Provides
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob

@Module(
    includes = [
        NetworkModule::class,
        RoomModule::class,
        RepositoryModule::class,
        ViewModelModule::class,
        MapperModule::class
    ]
)
class AppModule {

    @AppScope
    @Provides
    fun providesCoroutineScope(): CoroutineScope {
        return CoroutineScope(SupervisorJob() + Dispatchers.IO)
    }
}