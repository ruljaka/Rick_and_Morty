package com.ruslangrigoriev.rickandmorty.data.repository

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.ruslangrigoriev.rickandmorty.common.processApiCall
import com.ruslangrigoriev.rickandmorty.common.toRequestString
import com.ruslangrigoriev.rickandmorty.data.dto.characterDTO.CharacterDTO
import com.ruslangrigoriev.rickandmorty.data.dto.episodeDTO.EpisodeDTO
import com.ruslangrigoriev.rickandmorty.data.local.CharactersDao
import com.ruslangrigoriev.rickandmorty.data.local.EpisodesDao
import com.ruslangrigoriev.rickandmorty.data.paging.CharactersPagingSource
import com.ruslangrigoriev.rickandmorty.data.remote.ApiService
import com.ruslangrigoriev.rickandmorty.domain.repository.CharacterRepository
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.withContext
import javax.inject.Inject

class CharacterRepositoryImpl @Inject constructor(
    private val apiService: ApiService,
    private val charactersDao: CharactersDao,
    private val episodesDao: EpisodesDao

) : CharacterRepository {

    private val ioDispatcher: CoroutineDispatcher = Dispatchers.IO
    private var isNetworkAvaliable: Boolean = false

    override fun setNetworkStatus(status: Boolean) {
        isNetworkAvaliable = status
    }

    override suspend fun getCharacterById(characterID: Int): CharacterDTO {
        return withContext(ioDispatcher) {
            if (isNetworkAvaliable) {
                apiService.getCharacterById(characterID).processApiCall()
                    ?.apply { charactersDao.insertCharacter(this) }
            }
            charactersDao.getCharacterById(characterID)
        }
    }

    override suspend fun getCharacterEpisodes(ids: List<Int>): List<EpisodeDTO> {
        return withContext(ioDispatcher) {
            if (isNetworkAvaliable) {
                apiService.getCharacterEpisodes(ids.toRequestString()).processApiCall()
                    ?.apply { episodesDao.insertEpisodes(this) }

            }
            episodesDao.getListEpisodesByIds(ids)
        }
    }

    override fun getCharacters(
        name: String?, status: String?, species: String?, type: String?, gender: String?
    ): Flow<PagingData<CharacterDTO>> {
        return when (isNetworkAvaliable) {
            false -> {
                Pager(config = PagingConfig(pageSize = 20))
                {
                    charactersDao.getCharacters(
                        name = name,
                        status = status,
                        species = species,
                        type = type,
                        gender = gender
                    )
                }.flow.flowOn(ioDispatcher)
            }
            true -> {
                Pager(config = PagingConfig(pageSize = 20),
                    pagingSourceFactory = {
                        CharactersPagingSource(
                            name = name,
                            status = status,
                            species = species,
                            type = type,
                            gender = gender,
                            apiService = apiService,
                            charactersDao = charactersDao
                        )
                    }
                ).flow.flowOn(ioDispatcher)
            }
        }
    }
}