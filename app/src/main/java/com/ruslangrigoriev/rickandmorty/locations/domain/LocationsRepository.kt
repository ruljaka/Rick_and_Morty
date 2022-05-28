package com.ruslangrigoriev.rickandmorty.locations.domain

import androidx.paging.PagingData
import com.ruslangrigoriev.rickandmorty.core.data.dto.character_dto.Character
import com.ruslangrigoriev.rickandmorty.core.data.dto.location_dto.Location
import kotlinx.coroutines.flow.Flow

interface LocationsRepository {

    fun setNetworkStatus(status: Boolean)
    suspend fun getLocationById(locationID: Int): Location?
    suspend fun getLocationResidents(ids: List<Int>): List<Character>?
    fun getLocations(
        name: String? = null,
        type: String? = null,
        dimension: String? = null
    ): Flow<PagingData<Location>>

}