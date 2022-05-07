package com.ruslangrigoriev.rickandmorty.data.paging

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.ruslangrigoriev.rickandmorty.data.dto.locationDTO.LocationDTO
import com.ruslangrigoriev.rickandmorty.data.local.LocationsDao
import com.ruslangrigoriev.rickandmorty.data.remote.LocationsService
import com.ruslangrigoriev.rickandmorty.data.repository.getKey

class LocationsPagingSource(
    private val name: String? = null,
    private val type: String? = null,
    private val dimension: String? = null,
    private val locationsService: LocationsService,
    private val locationsDao: LocationsDao
) : PagingSource<Int, LocationDTO>() {

    private lateinit var responseData: List<LocationDTO>

    override fun getRefreshKey(state: PagingState<Int, LocationDTO>): Int? {
        return state.anchorPosition?.let { anchorPosition ->
            state.closestPageToPosition(anchorPosition)?.prevKey?.plus(1)
                ?: state.closestPageToPosition(anchorPosition)?.nextKey?.minus(1)
        }
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, LocationDTO> {
        return try {
            val currentPage = params.key ?: 1
            val response = locationsService.getLocations(
                page = currentPage,
                name = name,
                type = type,
                dimension = dimension,
            ).body()
            responseData = response?.locations ?: emptyList()
            if (responseData.isNotEmpty()) {
                locationsDao.insertLocations(responseData)
            }
            LoadResult.Page(
                data = responseData,
                prevKey = response?.info?.prev?.getKey(),
                nextKey = response?.info?.next?.getKey()
            )
        } catch (e: Throwable) {
            LoadResult.Error(e)
        }
    }

}