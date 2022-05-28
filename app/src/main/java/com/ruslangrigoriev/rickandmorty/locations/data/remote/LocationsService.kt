package com.ruslangrigoriev.rickandmorty.locations.data.remote

import com.ruslangrigoriev.rickandmorty.core.data.dto.character_dto.Character
import com.ruslangrigoriev.rickandmorty.core.data.dto.location_dto.Location
import com.ruslangrigoriev.rickandmorty.core.data.dto.location_dto.LocationResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface LocationsService {

    @GET("location")
    suspend fun getLocations(
        @Query("page") page: Int = 1,
        @Query("name") name: String? = null,
        @Query("type") type: String? = null,
        @Query("dimension") dimension: String? = null,
    ): Response<LocationResponse>

    @GET("location/{id}")
    suspend fun getLocationById(
        @Path("id") locationID: Int
    ): Response<Location>

    @GET("character/{ids}")
    suspend fun getLocationCharacters(
        @Path("ids") ids: String
    ): Response<List<Character>>
}