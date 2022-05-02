package com.ruslangrigoriev.rickandmorty.di

import com.ruslangrigoriev.rickandmorty.data.remote.ApiService
import com.ruslangrigoriev.rickandmorty.data.repository.CharacterRepositoryImpl
import com.ruslangrigoriev.rickandmorty.domain.repository.CharacterRepository
import com.ruslangrigoriev.rickandmorty.presentation.FragmentNavigator
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class AppModule() {

    @Singleton
    @Provides
    fun provideCharacterRepository(apiService: ApiService): CharacterRepository {
        return CharacterRepositoryImpl(apiService)
    }

    @Singleton
    @Provides
    fun provideFragmentNavigator(): FragmentNavigator {
        return FragmentNavigator()
    }

}