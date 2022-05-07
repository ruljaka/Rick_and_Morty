package com.ruslangrigoriev.rickandmorty.presentation.episodes

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.ruslangrigoriev.rickandmorty.data.dto.episodeDTO.EpisodeDTO
import com.ruslangrigoriev.rickandmorty.domain.useCases.episodes.GetEpisodesUseCase
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class EpisodesViewModel @Inject constructor(
    private val getEpisodesUseCase: GetEpisodesUseCase
) : ViewModel() {

    private var _episodesFlow: Flow<PagingData<EpisodeDTO>>? = null
    val episodesFlow: Flow<PagingData<EpisodeDTO>>?
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