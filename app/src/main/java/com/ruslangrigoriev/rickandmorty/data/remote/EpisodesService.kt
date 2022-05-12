package com.ruslangrigoriev.rickandmorty.data.remote

import com.ruslangrigoriev.rickandmorty.data.dto_and_entity.characterDTO.CharacterDTO
import com.ruslangrigoriev.rickandmorty.data.dto_and_entity.episodeDTO.EpisodeDTO
import com.ruslangrigoriev.rickandmorty.data.dto_and_entity.episodeDTO.EpisodeResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface EpisodesService {

    @GET("episode")
    suspend fun getEpisodes(
        @Query("page") page: Int = 1,
        @Query("name") name: String? = null,
        @Query("episode") episode: String? = null,
    ): Response<EpisodeResponse>

    @GET("episode/{id}")
    suspend fun getEpisode(
        @Path("id") episodeID: Int
    ): Response<EpisodeDTO>

    @GET("character/{ids}")
    suspend fun getEpisodeCharacters(
        @Path("ids") ids: String
    ): Response<List<CharacterDTO>>
}