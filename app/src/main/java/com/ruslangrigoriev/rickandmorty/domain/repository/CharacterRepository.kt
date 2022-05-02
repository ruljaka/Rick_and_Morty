package com.ruslangrigoriev.rickandmorty.domain.repository

import androidx.paging.PagingData
import com.ruslangrigoriev.rickandmorty.data.dto.characterDTO.CharacterDTO
import com.ruslangrigoriev.rickandmorty.data.dto.episodeDTO.EpisodeDTO
import kotlinx.coroutines.flow.Flow

interface CharacterRepository {

    fun getAllCharacters() : Flow<PagingData<CharacterDTO>>
    //suspend fun getCharacterById(characterID: Int): Flow<NetworkResult<CharacterDTO>>
    suspend fun getCharacterById(characterID: Int): CharacterDTO?
    suspend fun getCharacterEpisodes(ids: String) : List<EpisodeDTO>?
}