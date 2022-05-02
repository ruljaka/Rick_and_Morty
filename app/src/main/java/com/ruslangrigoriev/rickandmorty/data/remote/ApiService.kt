package com.ruslangrigoriev.rickandmorty.data.remote

import com.ruslangrigoriev.rickandmorty.data.dto.characterDTO.CharacterDTO
import com.ruslangrigoriev.rickandmorty.data.dto.characterDTO.CharacterResponse
import com.ruslangrigoriev.rickandmorty.data.dto.episodeDTO.EpisodeDTO
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiService {

    @GET("character")
    suspend fun getAllCharacters(
        @Query("page") page: Int = 1,
    ): Response<CharacterResponse>

    @GET("character/{id}")
    suspend fun getCharacterById(
        @Path("id") characterID: Int
    ): Response<CharacterDTO>

    @GET("episode/{ids}")
    suspend fun getCharacterEpisodes(
        @Path("ids") ids: String
    ): Response<List<EpisodeDTO>>
}