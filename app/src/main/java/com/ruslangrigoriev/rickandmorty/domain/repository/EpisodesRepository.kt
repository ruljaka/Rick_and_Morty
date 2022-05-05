package com.ruslangrigoriev.rickandmorty.domain.repository

import androidx.paging.PagingData
import com.ruslangrigoriev.rickandmorty.data.dto.characterDTO.CharacterDTO
import com.ruslangrigoriev.rickandmorty.data.dto.episodeDTO.EpisodeDTO
import kotlinx.coroutines.flow.Flow

interface EpisodesRepository {

    fun setNetworkStatus(status: Boolean)
    suspend fun getEpisodeById(characterID: Int): EpisodeDTO
    suspend fun getEpisodeCharacters(ids: List<Int>): List<CharacterDTO>?
    fun getEpisodes(
        name: String? = null,
        episode: String? = null
    ): Flow<PagingData<EpisodeDTO>>
}