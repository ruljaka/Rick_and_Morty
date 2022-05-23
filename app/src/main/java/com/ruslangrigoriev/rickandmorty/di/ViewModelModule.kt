package com.ruslangrigoriev.rickandmorty.di

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.ruslangrigoriev.rickandmorty.presentation.common.ViewModelFactory
import com.ruslangrigoriev.rickandmorty.presentation.ui.characterDetails.CharacterDetailsViewModel
import com.ruslangrigoriev.rickandmorty.presentation.ui.characters.CharactersViewModel
import com.ruslangrigoriev.rickandmorty.presentation.ui.episodeDetails.EpisodeDetailsViewModel
import com.ruslangrigoriev.rickandmorty.presentation.ui.episodes.EpisodesViewModel
import com.ruslangrigoriev.rickandmorty.presentation.ui.locationDetails.LocationDetailsViewModel
import com.ruslangrigoriev.rickandmorty.presentation.ui.locations.LocationsViewModel
import com.ruslangrigoriev.rickandmorty.presentation.ui.main.MainViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Module
abstract class ViewModelModule {

    @Binds
    internal abstract fun bindViewModelFactory(factory: ViewModelFactory): ViewModelProvider.Factory

    @Binds
    @IntoMap
    @ViewModelKey(MainViewModel::class)
    internal abstract fun bindMainViewModel(viewModel: MainViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(CharacterDetailsViewModel::class)
    internal abstract fun bindCharacterDetailsViewModel(viewModel: CharacterDetailsViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(CharactersViewModel::class)
    internal abstract fun bindCharactersViewModel(viewModel: CharactersViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(LocationDetailsViewModel::class)
    internal abstract fun bindLocationDetailsViewModel(viewModel: LocationDetailsViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(LocationsViewModel::class)
    internal abstract fun bindLocationsViewModel(viewModel: LocationsViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(EpisodeDetailsViewModel::class)
    internal abstract fun bindEpisodeDetailsViewModel(viewModel: EpisodeDetailsViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(EpisodesViewModel::class)
    internal abstract fun bindEpisodesViewModel(viewModel: EpisodesViewModel): ViewModel

}