package com.ruslangrigoriev.rickandmorty.domain.repository

import androidx.paging.PagingData
import com.ruslangrigoriev.rickandmorty.data.dto.characterDTO.CharacterDTO
import com.ruslangrigoriev.rickandmorty.data.dto.episodeDTO.EpisodeDTO
import kotlinx.coroutines.flow.Flow

interface CharacterRepository {

    suspend fun getCharacterById(characterID: Int): CharacterDTO?
    suspend fun getCharacterEpisodes(ids: String): List<EpisodeDTO>?
    fun getCharacters(
        name: String? = null,
        status: String? = null,
        species: String? = null,
        type: String? = null,
        gender: String? = null
    ) : Flow<PagingData<CharacterDTO>>
}