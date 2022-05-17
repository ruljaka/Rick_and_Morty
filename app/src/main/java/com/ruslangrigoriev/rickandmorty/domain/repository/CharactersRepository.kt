package com.ruslangrigoriev.rickandmorty.domain.repository

import androidx.paging.PagingData
import com.ruslangrigoriev.rickandmorty.data.dto_and_entity.characters.Character
import com.ruslangrigoriev.rickandmorty.data.dto_and_entity.episodes.Episode
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