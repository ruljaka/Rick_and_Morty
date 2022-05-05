package com.ruslangrigoriev.rickandmorty.data.remote

import com.ruslangrigoriev.rickandmorty.data.dto.characterDTO.CharacterDTO
import com.ruslangrigoriev.rickandmorty.data.dto.characterDTO.CharacterResponse
import com.ruslangrigoriev.rickandmorty.data.dto.episodeDTO.EpisodeDTO
import com.ruslangrigoriev.rickandmorty.data.dto.episodeDTO.EpisodeResponse
import com.ruslangrigoriev.rickandmorty.data.dto.locationDTO.LocationDTO
import com.ruslangrigoriev.rickandmorty.data.dto.locationDTO.LocationResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiService {

    @GET("character/{id}")
    suspend fun getCharacterById(
        @Path("id") characterID: Int
    ): Response<CharacterDTO>

    @GET("episode/{ids}")
    suspend fun getListEpisodesByIds(
        @Path("ids") ids: String
    ): Response<List<EpisodeDTO>>

    @GET("character/")
    suspend fun getCharacters(
        @Query("page") page: Int = 1,
        @Query("name") name: String? = null,
        @Query("status") status: String? = null,
        @Query("species") species: String? = null,
        @Query("type") type: String? = null,
        @Query("gender") gender: String? = null
    ): Response<CharacterResponse>

    @GET("episode/")
    suspend fun getEpisodes(
        @Query("page") page: Int = 1,
        @Query("name") name: String? = null,
        @Query("episode") episode: String? = null,
    ): Response<EpisodeResponse>

    @GET("episode/{id}")
    suspend fun getEpisodeById(
        @Path("id") episodeID: Int
    ): Response<EpisodeDTO>

    @GET("character/{ids}")
    suspend fun getListCharactersByIds(
        @Path("ids") ids: String
    ): Response<List<CharacterDTO>>

    @GET("location/")
    suspend fun getLocations(
        @Query("page") page: Int = 1,
        @Query("name") name: String? = null,
        @Query("type") type: String? = null,
        @Query("dimension") dimension: String? = null,
    ): Response<LocationResponse>

    @GET("location/{id}")
    suspend fun getLocationById(
        @Path("id") locationID: Int
    ): Response<LocationDTO>

}