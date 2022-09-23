package com.ruslangrigoriev.rickandmorty.domain.repository

import androidx.paging.PagingData
import com.ruslangrigoriev.rickandmorty.domain.entity.characters.Character
import com.ruslangrigoriev.rickandmorty.domain.entity.locations.Location
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