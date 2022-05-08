package com.ruslangrigoriev.rickandmorty.data.paging

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.ruslangrigoriev.rickandmorty.data.dto.episodeDTO.EpisodeDTO
import com.ruslangrigoriev.rickandmorty.data.local.EpisodesDao
import com.ruslangrigoriev.rickandmorty.data.remote.EpisodesService
import com.ruslangrigoriev.rickandmorty.data.getKey

class EpisodesPagingSource(
    private val name: String? = null,
    private val episode: String? = null,
    private val episodesService: EpisodesService,
    private val episodesDao: EpisodesDao
) : PagingSource<Int, EpisodeDTO>() {

    private lateinit var responseData: List<EpisodeDTO>

    override fun getRefreshKey(state: PagingState<Int, EpisodeDTO>): Int? {
        return state.anchorPosition?.let { anchorPosition ->
            state.closestPageToPosition(anchorPosition)?.prevKey?.plus(1)
                ?: state.closestPageToPosition(anchorPosition)?.nextKey?.minus(1)
        }
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, EpisodeDTO> {
        return try {
            val currentPage = params.key ?: 1
            val response = episodesService.getEpisodes(
                page = currentPage,
                name = name,
                episode = episode,
            ).body()
            responseData = response?.episodes ?: emptyList()
            if (responseData.isNotEmpty()) {
                episodesDao.insertEpisodes(responseData)
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