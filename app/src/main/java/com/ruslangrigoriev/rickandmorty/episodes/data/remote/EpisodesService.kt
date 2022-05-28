package com.ruslangrigoriev.rickandmorty.episodes.data.remote

import com.ruslangrigoriev.rickandmorty.core.data.dto.character_dto.Character
import com.ruslangrigoriev.rickandmorty.core.data.dto.episode_dto.Episode
import com.ruslangrigoriev.rickandmorty.core.data.dto.episode_dto.EpisodeResponse
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
    suspend fun getEpisodeById(
        @Path("id") episodeID: Int
    ): Response<Episode>

    @GET("character/{ids}")
    suspend fun getEpisodeCharacters(
        @Path("ids") ids: String
    ): Response<List<Character>>
}