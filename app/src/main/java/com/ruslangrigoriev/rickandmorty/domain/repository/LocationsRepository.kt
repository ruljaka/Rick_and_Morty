package com.ruslangrigoriev.rickandmorty.domain.repository

import androidx.paging.PagingData
import com.ruslangrigoriev.rickandmorty.data.dto.characterDTO.CharacterDTO
import com.ruslangrigoriev.rickandmorty.data.dto.locationDTO.LocationDTO
import kotlinx.coroutines.flow.Flow

interface LocationsRepository {

    fun setNetworkStatus(status: Boolean)
    suspend fun getLocationById(locationID: Int): LocationDTO
    suspend fun getLocationResidents(ids: List<Int>): List<CharacterDTO>?
    fun getLocations(
        name: String? = null,
        type: String? = null,
        dimension: String? = null
    ): Flow<PagingData<LocationDTO>>

}