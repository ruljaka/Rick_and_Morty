package com.ruslangrigoriev.rickandmorty.data.repository

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.ruslangrigoriev.rickandmorty.data.dto.characterDTO.CharacterDTO
import com.ruslangrigoriev.rickandmorty.data.dto.locationDTO.LocationDTO
import com.ruslangrigoriev.rickandmorty.data.local.CharactersDao
import com.ruslangrigoriev.rickandmorty.data.local.LocationsDao
import com.ruslangrigoriev.rickandmorty.data.paging.LocationsPagingSource
import com.ruslangrigoriev.rickandmorty.data.remote.LocationsService
import com.ruslangrigoriev.rickandmorty.domain.repository.LocationsRepository
import com.ruslangrigoriev.rickandmorty.presentation.common.toRequestString
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

class LocationsRepositoryImpl @Inject constructor(
    private val locationsService: LocationsService,
    private val charactersDao: CharactersDao,
    private val locationsDao: LocationsDao
) : LocationsRepository {

    private val ioDispatcher: CoroutineDispatcher = Dispatchers.IO
    private var isNetworkAvailable: Boolean = false

    override fun setNetworkStatus(status: Boolean) {
        isNetworkAvailable = status
    }

    override suspend fun getLocationById(locationID: Int): LocationDTO? =
        getRemoteOrCachedData(
            isNetworkAvailable,
            { locationsService.getLocation(locationID) },
            { locationsDao.getLocationById(locationID) },
            { locationsDao.insertLocation(it) }
        )

    override suspend fun getLocationResidents(ids: List<Int>): List<CharacterDTO>? =
        getRemoteOrCachedData(
            isNetworkAvailable,
            { locationsService.getLocationCharacters(ids.toRequestString()) },
            { charactersDao.getListCharactersByIds(ids) },
            { charactersDao.insertCharacters(it) }
        )

    override fun getLocations(
        name: String?, type: String?, dimension: String?
    ): Flow<PagingData<LocationDTO>> {
        val pagingConfig = PagingConfig(pageSize = 20, enablePlaceholders = false)
        return when (isNetworkAvailable) {
            false -> {
                Pager(pagingConfig) { locationsDao.getLocations(name, type, dimension) }
                    .flow.flowOn(ioDispatcher)
            }
            true -> {
                Pager(pagingConfig) {
                    LocationsPagingSource(name, type, dimension, locationsService, locationsDao)
                }
                    .flow.flowOn(ioDispatcher)
            }
        }
    }
}