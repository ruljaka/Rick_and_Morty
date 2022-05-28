package com.ruslangrigoriev.rickandmorty.characters.data.repository

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.ruslangrigoriev.rickandmorty.characters.data.local.CharactersDao
import com.ruslangrigoriev.rickandmorty.characters.data.remote.CharactersPagingSource
import com.ruslangrigoriev.rickandmorty.characters.data.remote.CharactersService
import com.ruslangrigoriev.rickandmorty.characters.domain.CharactersRepository
import com.ruslangrigoriev.rickandmorty.core.data.dto.character_dto.Character
import com.ruslangrigoriev.rickandmorty.core.data.dto.episode_dto.Episode
import com.ruslangrigoriev.rickandmorty.core.getRemoteOrCachedData
import com.ruslangrigoriev.rickandmorty.core.toRequestString
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.launch
import javax.inject.Inject

class CharactersRepositoryImpl @Inject constructor(
    private val charactersService: CharactersService,
    private val charactersDao: CharactersDao
) : CharactersRepository {

    private val ioDispatcher: CoroutineDispatcher = Dispatchers.IO
    private var isNetworkAvailable: Boolean = false
    private var isCacheCleared: Boolean = true

    override fun setNetworkStatus(status: Boolean) {
        isNetworkAvailable = status
        isCacheCleared = !status
    }

    private fun clearCache() {
        CoroutineScope(ioDispatcher).launch {
            charactersDao.deleteAll()
            isCacheCleared = true
        }
    }

    override suspend fun getCharacterById(characterID: Int): Character? =
        getRemoteOrCachedData(
            isNetworkAvailable,
            { charactersService.getCharacterById(characterID) },
            { charactersDao.getCharacterById(characterID) },
            { charactersDao.insertCharacter(it) }
        )

    override suspend fun getCharacterEpisodes(ids: List<Int>): List<Episode>? =
        getRemoteOrCachedData(
            isNetworkAvailable,
            { charactersService.getCharacterEpisodes(ids.toRequestString()) },
            { charactersDao.getListEpisodesByIds(ids) },
            { charactersDao.insertEpisodes(it) }
        )

    override fun getCharacters(
        name: String?, status: String?, species: String?, type: String?, gender: String?
    ): Flow<PagingData<Character>> {
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

}