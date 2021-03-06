package com.ruslangrigoriev.rickandmorty.episodes.data.remote

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.ruslangrigoriev.rickandmorty.core.data.dto.episode_dto.Episode
import com.ruslangrigoriev.rickandmorty.episodes.data.local.EpisodesDao

class EpisodesPagingSource(
    private val name: String? = null,
    private val episode: String? = null,
    private val episodesService: EpisodesService,
    private val episodesDao: EpisodesDao
) : PagingSource<Int, Episode>() {

    private lateinit var responseData: List<Episode>

    override fun getRefreshKey(state: PagingState<Int, Episode>): Int? {
        return state.anchorPosition?.let { anchorPosition ->
            state.closestPageToPosition(anchorPosition)?.prevKey?.plus(1)
                ?: state.closestPageToPosition(anchorPosition)?.nextKey?.minus(1)
        }
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Episode> {
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
                prevKey = if (currentPage == 1) null else currentPage.minus(1),
                nextKey = if (responseData.isEmpty()) null else currentPage.plus(1)
            )
        } catch (e: Throwable) {
            LoadResult.Error(e)
        }
    }

}