package com.ruslangrigoriev.rickandmorty.locations.data.remote

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.ruslangrigoriev.rickandmorty.core.data.dto.location_dto.Location
import com.ruslangrigoriev.rickandmorty.locations.data.local.LocationsDao

class LocationsPagingSource(
    private val name: String? = null,
    private val type: String? = null,
    private val dimension: String? = null,
    private val locationsService: LocationsService,
    private val locationsDao: LocationsDao
) : PagingSource<Int, Location>() {

    private lateinit var responseData: List<Location>

    override fun getRefreshKey(state: PagingState<Int, Location>): Int? {
        return state.anchorPosition?.let { anchorPosition ->
            state.closestPageToPosition(anchorPosition)?.prevKey?.plus(1)
                ?: state.closestPageToPosition(anchorPosition)?.nextKey?.minus(1)
        }
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Location> {
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
                prevKey = if (currentPage == 1) null else currentPage.minus(1),
                nextKey = if (responseData.isEmpty()) null else currentPage.plus(1)
            )
        } catch (e: Throwable) {
            LoadResult.Error(e)
        }
    }

}