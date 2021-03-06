package com.ruslangrigoriev.rickandmorty.characters.data.remote

import com.ruslangrigoriev.rickandmorty.core.data.dto.character_dto.Character
import com.ruslangrigoriev.rickandmorty.core.data.dto.character_dto.CharacterResponse
import com.ruslangrigoriev.rickandmorty.core.data.dto.episode_dto.Episode
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface CharactersService {

    @GET("character")
    suspend fun getCharacters(
        @Query("page") page: Int = 1,
        @Query("name") name: String? = null,
        @Query("status") status: String? = null,
        @Query("species") species: String? = null,
        @Query("type") type: String? = null,
        @Query("gender") gender: String? = null
    ): Response<CharacterResponse>

    @GET("character/{id}")
    suspend fun getCharacterById(
        @Path("id") characterID: Int
    ): Response<Character>

    @GET("episode/{ids}")
    suspend fun getCharacterEpisodes(
        @Path("ids") ids: String
    ): Response<List<Episode>>
}