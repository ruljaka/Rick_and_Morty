package com.ruslangrigoriev.rickandmorty.data.repository

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.ruslangrigoriev.rickandmorty.common.processApiCall
import com.ruslangrigoriev.rickandmorty.data.dto.characterDTO.CharacterDTO
import com.ruslangrigoriev.rickandmorty.data.dto.episodeDTO.EpisodeDTO
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
    private val apiService: ApiService
) : CharacterRepository {

    private val ioDispatcher: CoroutineDispatcher = Dispatchers.IO

    override fun getAllCharacters(): Flow<PagingData<CharacterDTO>> {
        return Pager(
            config = PagingConfig(pageSize = 20),
            pagingSourceFactory = { CharactersPagingSource(apiService) }
        ).flow.flowOn(ioDispatcher)
    }

    override suspend fun getCharacterById(characterID: Int): CharacterDTO? {
        return withContext(ioDispatcher) {
            apiService.getCharacterById(characterID).processApiCall()
        }
    }

    override suspend fun getCharacterEpisodes(ids: String): List<EpisodeDTO>? {
        return withContext(ioDispatcher) {
            apiService.getCharacterEpisodes(ids).processApiCall()
        }
    }

//    override suspend fun getCharacterById(characterID: Int): Flow<NetworkResult<CharacterDTO>> {
//        return flow {
//            emit(NetworkResult.Loading())
//            emit(safeApiCall { apiService.getCharacterById(characterID) })
//        }.flowOn(ioDispatcher)
//
//        //TODO
//        // переписать без флоу, без  networkResult
//    }
}