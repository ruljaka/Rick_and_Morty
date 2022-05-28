package com.ruslangrigoriev.rickandmorty.episodes.presentation.list

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.ruslangrigoriev.rickandmorty.core.data.dto.episode_dto.Episode
import com.ruslangrigoriev.rickandmorty.episodes.domain.useCases.GetEpisodesUseCase
import com.ruslangrigoriev.rickandmorty.episodes.presentation.list.filter.EpisodesFilter
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class EpisodesViewModel @Inject constructor(
    private val getEpisodesUseCase: GetEpisodesUseCase
) : ViewModel() {

    private var _episodesFlow: Flow<PagingData<Episode>>? = null
    val episodesFlow: Flow<PagingData<Episode>>?
        get() = _episodesFlow

    init {
         getEpisodes()
    }

    fun getEpisodes(filter: EpisodesFilter? = null) {
        _episodesFlow = getEpisodesUseCase(
            name = filter?.name,
            episode = filter?.episode,
        ).cachedIn(viewModelScope)
    }
}