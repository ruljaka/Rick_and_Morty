package com.ruslangrigoriev.rickandmorty.episodes.data.repository

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.ruslangrigoriev.rickandmorty.core.data.dto.character_dto.Character
import com.ruslangrigoriev.rickandmorty.core.data.dto.episode_dto.Episode
import com.ruslangrigoriev.rickandmorty.core.getRemoteOrCachedData
import com.ruslangrigoriev.rickandmorty.characters.data.local.CharactersDao
import com.ruslangrigoriev.rickandmorty.core.toRequestString
import com.ruslangrigoriev.rickandmorty.episodes.domain.EpisodesRepository
import com.ruslangrigoriev.rickandmorty.episodes.data.local.EpisodesDao
import com.ruslangrigoriev.rickandmorty.episodes.data.remote.EpisodesPagingSource
import com.ruslangrigoriev.rickandmorty.episodes.data.remote.EpisodesService
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.launch
import javax.inject.Inject

class EpisodesRepositoryImpl @Inject constructor(
    private val episodesService: EpisodesService,
    private val episodesDao: EpisodesDao
) : EpisodesRepository {

    private val ioDispatcher: CoroutineDispatcher = Dispatchers.IO
    private var isNetworkAvailable: Boolean = false
    private var isCacheCleared: Boolean = true

    override fun setNetworkStatus(status: Boolean) {
        isNetworkAvailable = status
        isCacheCleared = !status
    }

    private fun clearCache() {
        CoroutineScope(ioDispatcher).launch {
            episodesDao.deleteAll()
            isCacheCleared = true
        }
    }

    override suspend fun getEpisodeById(episodeID: Int): Episode? =
        getRemoteOrCachedData(
            isNetworkAvailable,
            { episodesService.getEpisodeById(episodeID) },
            { episodesDao.getEpisodeById(episodeID) },
            { episodesDao.insertEpisode(it) }
        )

    override suspend fun getEpisodeCharacters(ids: List<Int>): List<Character>? =
        getRemoteOrCachedData(
            isNetworkAvailable,
            { episodesService.getEpisodeCharacters(ids.toRequestString()) },
            { episodesDao.getListCharactersByIds(ids) },
            { episodesDao.insertCharacters(it) }
        )

    override fun getEpisodes(
        name: String?, episode: String?
    ): Flow<PagingData<Episode>> {
        if (!isCacheCleared) clearCache()
        val pagingConfig = PagingConfig(pageSize = 20, enablePlaceholders = false)
        return when (isNetworkAvailable) {
            false -> {
                Pager(pagingConfig) { episodesDao.getEpisodes(name, episode) }
                    .flow.flowOn(ioDispatcher)
            }
            true -> {
                Pager(pagingConfig) {
                    EpisodesPagingSource(name, episode, episodesService, episodesDao)
                }
                    .flow.flowOn(ioDispatcher)
            }
        }
    }
}