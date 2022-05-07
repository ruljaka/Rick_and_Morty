package com.ruslangrigoriev.rickandmorty.data.repository

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.ruslangrigoriev.rickandmorty.common.safeApiCall
import com.ruslangrigoriev.rickandmorty.common.toRequestString
import com.ruslangrigoriev.rickandmorty.data.dto.characterDTO.CharacterDTO
import com.ruslangrigoriev.rickandmorty.data.dto.episodeDTO.EpisodeDTO
import com.ruslangrigoriev.rickandmorty.data.local.CharactersDao
import com.ruslangrigoriev.rickandmorty.data.local.EpisodesDao
import com.ruslangrigoriev.rickandmorty.data.paging.EpisodesPagingSource
import com.ruslangrigoriev.rickandmorty.data.remote.ApiService
import com.ruslangrigoriev.rickandmorty.domain.repository.EpisodesRepository
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.withContext
import javax.inject.Inject

class EpisodesRepositoryImpl @Inject constructor(
    private val apiService: ApiService,
    private val charactersDao: CharactersDao,
    private val episodesDao: EpisodesDao

) : EpisodesRepository {

    private val ioDispatcher: CoroutineDispatcher = Dispatchers.IO
    private var isNetworkAvailable: Boolean = false

    override fun setNetworkStatus(status: Boolean) {
        isNetworkAvailable = status
    }

    override suspend fun getEpisodeById(episodeID: Int): EpisodeDTO? {
        return withContext(ioDispatcher) {
            if (isNetworkAvailable) {
                safeApiCall { apiService.getEpisodeById(episodeID).body() }
                    ?.apply { episodesDao.insertEpisode(this) }
            }
            episodesDao.getEpisodeById(episodeID)
        }
    }

    override suspend fun getEpisodeCharacters(ids: List<Int>): List<CharacterDTO> {
        return withContext(ioDispatcher) {
            if (isNetworkAvailable) {
                safeApiCall { apiService.getListCharactersByIds(ids.toRequestString()).body() }
                    ?.apply { charactersDao.insertCharacters(this) }
            }
            charactersDao.getListCharactersByIds(ids)
        }
    }

    override fun getEpisodes(
        name: String?, episode: String?
    ): Flow<PagingData<EpisodeDTO>> {
        return when (isNetworkAvailable) {
            false -> {
                Pager(config = PagingConfig(pageSize = 20))
                {
                    episodesDao.getEpisodes(
                        name = name,
                        episode = episode,
                    )
                }.flow.flowOn(ioDispatcher)
            }
            true -> {
                Pager(config = PagingConfig(pageSize = 20),
                    pagingSourceFactory = {
                        EpisodesPagingSource(
                            name = name,
                            episode = episode,
                            apiService = apiService,
                            episodesDao = episodesDao
                        )
                    }
                ).flow.flowOn(ioDispatcher)
            }
        }
    }
}