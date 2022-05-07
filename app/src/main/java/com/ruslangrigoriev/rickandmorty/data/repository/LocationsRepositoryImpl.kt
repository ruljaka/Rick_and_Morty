package com.ruslangrigoriev.rickandmorty.data.repository

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.ruslangrigoriev.rickandmorty.common.safeApiCall
import com.ruslangrigoriev.rickandmorty.common.toRequestString
import com.ruslangrigoriev.rickandmorty.data.dto.characterDTO.CharacterDTO
import com.ruslangrigoriev.rickandmorty.data.dto.locationDTO.LocationDTO
import com.ruslangrigoriev.rickandmorty.data.local.CharactersDao
import com.ruslangrigoriev.rickandmorty.data.local.LocationsDao
import com.ruslangrigoriev.rickandmorty.data.paging.LocationsPagingSource
import com.ruslangrigoriev.rickandmorty.data.remote.ApiService
import com.ruslangrigoriev.rickandmorty.domain.repository.LocationsRepository
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.withContext
import javax.inject.Inject

class LocationsRepositoryImpl @Inject constructor(
    private val apiService: ApiService,
    private val charactersDao: CharactersDao,
    private val locationsDao: LocationsDao

) : LocationsRepository {

    private val ioDispatcher: CoroutineDispatcher = Dispatchers.IO
    private var isNetworkAvailable: Boolean = false

    override fun setNetworkStatus(status: Boolean) {
        isNetworkAvailable = status
    }

    override suspend fun getLocationById(locationID: Int): LocationDTO? {
        return withContext(ioDispatcher) {
            if (isNetworkAvailable) {
                safeApiCall { apiService.getLocationById(locationID).body() }
                    ?.apply { locationsDao.insertLocation(this) }
            }
            locationsDao.getLocationById(locationID)
        }
    }

    override suspend fun getLocationResidents(ids: List<Int>): List<CharacterDTO> {
        return withContext(ioDispatcher) {
            if (isNetworkAvailable) {
                safeApiCall { apiService.getListCharactersByIds(ids.toRequestString()).body() }
                    ?.apply { charactersDao.insertCharacters(this) }
            }
            charactersDao.getListCharactersByIds(ids)
        }
    }

    override fun getLocations(
        name: String?, type: String?, dimension: String?
    ): Flow<PagingData<LocationDTO>> {
        return when (isNetworkAvailable) {
            false -> {
                Pager(config = PagingConfig(pageSize = 20))
                {
                    locationsDao.getLocations(
                        name = name,
                        type = type,
                        dimension = dimension,
                    )
                }.flow.flowOn(ioDispatcher)
            }
            true -> {
                Pager(config = PagingConfig(pageSize = 20),
                    pagingSourceFactory = {
                        LocationsPagingSource(
                            name = name,
                            type = type,
                            dimension = dimension,
                            apiService = apiService,
                            locationsDao = locationsDao
                        )
                    }
                ).flow.flowOn(ioDispatcher)
            }
        }
    }
}