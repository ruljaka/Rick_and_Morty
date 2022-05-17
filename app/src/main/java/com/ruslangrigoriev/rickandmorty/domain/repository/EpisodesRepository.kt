package com.ruslangrigoriev.rickandmorty.domain.repository

import androidx.paging.PagingData
import com.ruslangrigoriev.rickandmorty.data.dto_and_entity.characters.Character
import com.ruslangrigoriev.rickandmorty.data.dto_and_entity.episodes.Episode
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