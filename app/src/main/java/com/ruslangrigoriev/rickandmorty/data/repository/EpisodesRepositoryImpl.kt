package com.ruslangrigoriev.rickandmorty.data.repository

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.ruslangrigoriev.rickandmorty.data.source.local.CharactersDao
import com.ruslangrigoriev.rickandmorty.data.source.local.EpisodesDao
import com.ruslangrigoriev.rickandmorty.data.source.remote.EpisodesService
import com.ruslangrigoriev.rickandmorty.data.source.remote.paging.EpisodesPagingSource
import com.ruslangrigoriev.rickandmorty.domain.entity.characters.Character
import com.ruslangrigoriev.rickandmorty.domain.entity.episodes.Episode
import com.ruslangrigoriev.rickandmorty.domain.repository.EpisodesRepository
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.launch
import javax.inject.Inject

class EpisodesRepositoryImpl @Inject constructor(
    private val coroutineScope: CoroutineScope,
    private val episodesService: EpisodesService,
    private val charactersDao: CharactersDao,
    private val episodesDao: EpisodesDao
) : EpisodesRepository {

    private val ioDispatcher: CoroutineDispatcher = Dispatchers.IO
    private var isNetworkAvailable: Boolean = false
    private var isCacheCleared: Boolean = true

    override fun setNetworkStatus(status: Boolean) {
        isNetworkAvailable = status
        isCacheCleared = !status
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
            { charactersDao.getListCharactersByIds(ids) },
            { charactersDao.insertCharacters(it) }
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
                }.flow.flowOn(ioDispatcher)
            }
        }
    }

    private fun clearCache() {
        coroutineScope.launch {
            episodesDao.deleteAll()
            isCacheCleared = true
        }
    }
}