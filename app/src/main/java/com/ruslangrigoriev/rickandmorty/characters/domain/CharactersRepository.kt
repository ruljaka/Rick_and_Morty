package com.ruslangrigoriev.rickandmorty.characters.domain

import androidx.paging.PagingData
import com.ruslangrigoriev.rickandmorty.core.data.dto.character_dto.Character
import com.ruslangrigoriev.rickandmorty.core.data.dto.episode_dto.Episode
import kotlinx.coroutines.flow.Flow

interface CharactersRepository {

    fun setNetworkStatus(status: Boolean)
    suspend fun getCharacterById(characterID: Int): Character?
    suspend fun getCharacterEpisodes(ids: List<Int>): List<Episode>?
    fun getCharacters(
        name: String? = null,
        status: String? = null,
        species: String? = null,
        type: String? = null,
        gender: String? = null
    ): Flow<PagingData<Character>>
}