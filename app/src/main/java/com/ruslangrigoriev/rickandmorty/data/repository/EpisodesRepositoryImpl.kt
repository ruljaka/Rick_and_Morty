package com.ruslangrigoriev.rickandmorty.data.repository

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.ruslangrigoriev.rickandmorty.data.dto.characterDTO.CharacterDTO
import com.ruslangrigoriev.rickandmorty.data.dto.episodeDTO.EpisodeDTO
import com.ruslangrigoriev.rickandmorty.data.getRemoteOrCachedData
import com.ruslangrigoriev.rickandmorty.data.local.CharactersDao
import com.ruslangrigoriev.rickandmorty.data.local.EpisodesDao
import com.ruslangrigoriev.rickandmorty.data.paging.EpisodesPagingSource
import com.ruslangrigoriev.rickandmorty.data.remote.EpisodesService
import com.ruslangrigoriev.rickandmorty.data.toRequestString
import com.ruslangrigoriev.rickandmorty.domain.repository.EpisodesRepository
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

class EpisodesRepositoryImpl @Inject constructor(
    private val episodesService: EpisodesService,
    private val charactersDao: CharactersDao,
    private val episodesDao: EpisodesDao
) : EpisodesRepository {

    private val ioDispatcher: CoroutineDispatcher = Dispatchers.IO
    private var isNetworkAvailable: Boolean = false

    override fun setNetworkStatus(status: Boolean) {
        isNetworkAvailable = status
    }

    override suspend fun getEpisodeById(episodeID: Int): EpisodeDTO? =
        getRemoteOrCachedData(
            isNetworkAvailable,
            { episodesService.getEpisode(episodeID) },
            { episodesDao.getEpisodeById(episodeID) },
            { episodesDao.insertEpisode(it) }
        )

    override suspend fun getEpisodeCharacters(ids: List<Int>): List<CharacterDTO>? =
        getRemoteOrCachedData(
            isNetworkAvailable,
            { episodesService.getEpisodeCharacters(ids.toRequestString()) },
            { charactersDao.getListCharactersByIds(ids) },
            { charactersDao.insertCharacters(it) }
        )

    override fun getEpisodes(
        name: String?, episode: String?
    ): Flow<PagingData<EpisodeDTO>> {
        val pagingConfig = PagingConfig(pageSize = 20, enablePlaceholders = false)
        return when (isNetworkAvailable) {
            false -> {
                Pager(pagingConfig) { episodesDao.getEpisodes(name, episode) }
                    .flow.flowOn(ioDispatcher)
            }
            true -> {
                Pager(pagingConfig) {
                    EpisodesPagingSource(
                        name,
                        episode,
                        episodesService,
                        episodesDao
                    )
                }
                    .flow.flowOn(ioDispatcher)
            }
        }
    }
}