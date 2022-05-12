package com.ruslangrigoriev.rickandmorty.data.repository

import android.util.Log
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.ruslangrigoriev.rickandmorty.data.dto_and_entity.characterDTO.CharacterDTO
import com.ruslangrigoriev.rickandmorty.data.dto_and_entity.episodeDTO.EpisodeDTO
import com.ruslangrigoriev.rickandmorty.data.getRemoteOrCachedData
import com.ruslangrigoriev.rickandmorty.data.local.CharactersDao
import com.ruslangrigoriev.rickandmorty.data.local.EpisodesDao
import com.ruslangrigoriev.rickandmorty.data.paging.CharactersPagingSource
import com.ruslangrigoriev.rickandmorty.data.remote.CharactersService
import com.ruslangrigoriev.rickandmorty.data.toRequestString
import com.ruslangrigoriev.rickandmorty.domain.repository.CharactersRepository
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.launch
import javax.inject.Inject

class CharactersRepositoryImpl @Inject constructor(
    private val coroutineScope: CoroutineScope,
    private val charactersService: CharactersService,
    private val charactersDao: CharactersDao,
    private val episodesDao: EpisodesDao
) : CharactersRepository {

    private val ioDispatcher: CoroutineDispatcher = Dispatchers.IO
    private var isNetworkAvailable: Boolean = false
    private var isCacheCleared: Boolean = false

    override fun setNetworkStatus(status: Boolean) {
        isNetworkAvailable = status
        isCacheCleared = !status
    }

    private fun clearCache() {
        coroutineScope.launch {
            if (isNetworkAvailable) {
                Log.i("TAG", "clearCache")
                charactersDao.deleteAll()
                isCacheCleared = true
            }
        }
    }

    override suspend fun getCharacterById(characterID: Int): CharacterDTO? =
        getRemoteOrCachedData(
            isNetworkAvailable,
            { charactersService.getCharacter(characterID) },
            { charactersDao.getCharacterById(characterID) },
            { charactersDao.insertCharacter(it) }
        )

    override suspend fun getCharacterEpisodes(ids: List<Int>): List<EpisodeDTO>? =
        getRemoteOrCachedData(
            isNetworkAvailable,
            { charactersService.getCharacterEpisodes(ids.toRequestString()) },
            { episodesDao.getListEpisodesByIds(ids) },
            { episodesDao.insertEpisodes(it) }
        )

    override fun getCharacters(
        name: String?, status: String?, species: String?, type: String?, gender: String?
    ): Flow<PagingData<CharacterDTO>> {
        if (!isCacheCleared) clearCache()
        val pagingConfig = PagingConfig(pageSize = 20, enablePlaceholders = false)
        return when (isNetworkAvailable) {
            false -> {
                Pager(pagingConfig) {
                    charactersDao.getCharacters(name, status, species, type, gender)
                }
                    .flow.flowOn(ioDispatcher)
            }
            true -> {
                Pager(pagingConfig) {
                    CharactersPagingSource(
                        name, status, species, type, gender, charactersService, charactersDao
                    )
                }.flow.flowOn(ioDispatcher)
            }
        }
    }

//    @OptIn(ExperimentalPagingApi::class)
//    override fun getCharacters(
//        name: String?, status: String?, species: String?, type: String?, gender: String?
//    ): Flow<PagingData<CharacterDTO>> {
//        val pagingSourceFactory =
//            { database.getCharactersDao().getCharacters(name = name,status= status, species = species, type = type,gender= gender) }
//        val pagingConfig = PagingConfig(pageSize = 20, enablePlaceholders = false)
//        return Pager(
//            pagingConfig,
//            remoteMediator = СharacterRemoteMediator(
//                name = name,
//                status = status,
//                species = species,
//                type = type,
//                gender = gender,
//                charactersService,
//                database
//            ),
//            pagingSourceFactory = pagingSourceFactory
//        ).flow.flowOn(ioDispatcher)
//    }

}