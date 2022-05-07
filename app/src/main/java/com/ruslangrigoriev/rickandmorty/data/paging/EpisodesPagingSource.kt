package com.ruslangrigoriev.rickandmorty.data.paging

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.ruslangrigoriev.rickandmorty.data.dto.episodeDTO.EpisodeDTO
import com.ruslangrigoriev.rickandmorty.data.dto.episodeDTO.EpisodeResponse
import com.ruslangrigoriev.rickandmorty.data.local.EpisodesDao
import com.ruslangrigoriev.rickandmorty.data.remote.ApiService
import retrofit2.Response

class EpisodesPagingSource(
    private val name: String? = null,
    private val episode: String? = null,
    private val apiService: ApiService,
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
            val response = apiService.getEpisodes(
                page = currentPage,
                name = name,
                episode = episode,
            )
            responseData = response.body()?.episodes ?: emptyList()
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