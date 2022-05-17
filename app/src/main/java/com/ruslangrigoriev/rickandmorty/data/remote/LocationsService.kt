package com.ruslangrigoriev.rickandmorty.data.remote

import com.ruslangrigoriev.rickandmorty.data.dto_and_entity.characters.Character
import com.ruslangrigoriev.rickandmorty.data.dto_and_entity.locations.Location
import com.ruslangrigoriev.rickandmorty.data.dto_and_entity.locations.LocationResponse
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