package com.ruslangrigoriev.rickandmorty.data.repository

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.ruslangrigoriev.rickandmorty.data.source.local.CharactersDao
import com.ruslangrigoriev.rickandmorty.data.source.local.LocationsDao
import com.ruslangrigoriev.rickandmorty.data.source.remote.LocationsService
import com.ruslangrigoriev.rickandmorty.data.source.remote.paging.LocationsPagingSource
import com.ruslangrigoriev.rickandmorty.domain.entity.characters.Character
import com.ruslangrigoriev.rickandmorty.domain.entity.locations.Location
import com.ruslangrigoriev.rickandmorty.domain.repository.LocationsRepository
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.launch
import javax.inject.Inject

class LocationsRepositoryImpl @Inject constructor(
    private val coroutineScope: CoroutineScope,
    private val locationsService: LocationsService,
    private val charactersDao: CharactersDao,
    private val locationsDao: LocationsDao
) : LocationsRepository {

    private val ioDispatcher: CoroutineDispatcher = Dispatchers.IO
    private var isNetworkAvailable: Boolean = false
    private var isCacheCleared: Boolean = true

    override fun setNetworkStatus(status: Boolean) {
        isNetworkAvailable = status
        isCacheCleared = !status
    }

    override suspend fun getLocationById(locationID: Int): Location? =
        getRemoteOrCachedData(
            isNetworkAvailable,
            { locationsService.getLocationById(locationID) },
            { locationsDao.getLocationById(locationID) },
            { locationsDao.insertLocation(it) }
        )

    override suspend fun getLocationResidents(ids: List<Int>): List<Character>? =
        getRemoteOrCachedData(
            isNetworkAvailable,
            { locationsService.getLocationCharacters(ids.toRequestString()) },
            { charactersDao.getListCharactersByIds(ids) },
            { charactersDao.insertCharacters(it) }
        )

    override fun getLocations(
        name: String?, type: String?, dimension: String?
    ): Flow<PagingData<Location>> {
        if (!isCacheCleared) clearCache()
        val pagingConfig = PagingConfig(pageSize = 20, enablePlaceholders = false)
        return when (isNetworkAvailable) {
            false -> {
                Pager(pagingConfig) { locationsDao.getLocations(name, type, dimension) }
                    .flow.flowOn(ioDispatcher)
            }
            true -> {
                Pager(pagingConfig) {
                    LocationsPagingSource(name, type, dimension, locationsService, locationsDao)
                }.flow.flowOn(ioDispatcher)
            }
        }
    }

    private fun clearCache() {
        coroutineScope.launch {
            locationsDao.deleteAll()
            isCacheCleared = true
        }
    }
}