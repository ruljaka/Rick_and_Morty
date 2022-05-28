package com.ruslangrigoriev.rickandmorty.episodes.domain

import androidx.paging.PagingData
import com.ruslangrigoriev.rickandmorty.core.data.dto.character_dto.Character
import com.ruslangrigoriev.rickandmorty.core.data.dto.episode_dto.Episode
import kotlinx.coroutines.flow.Flow

interface EpisodesRepository {

    fun setNetworkStatus(status: Boolean)
    suspend fun getEpisodeById(episodeID: Int): Episode?
    suspend fun getEpisodeCharacters(ids: List<Int>): List<Character>?
    fun getEpisodes(
        name: String? = null,
        episode: String? = null
    ): Flow<PagingData<Episode>>
}