package com.ruslangrigoriev.rickandmorty.data.paging

import android.util.Log
import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.ruslangrigoriev.rickandmorty.data.dto.characterDTO.CharacterDTO
import com.ruslangrigoriev.rickandmorty.data.dto.characterDTO.CharacterResponse
import com.ruslangrigoriev.rickandmorty.data.remote.ApiService
import retrofit2.Response

class CharactersPagingSource(
    private val name: String? = null,
    private val status: String? = null,
    private val species: String? = null,
    private val type: String? = null,
    private val gender: String? = null,
    private val apiService: ApiService,
) : PagingSource<Int, CharacterDTO>() {

    private lateinit var responseData: List<CharacterDTO>

    override fun getRefreshKey(state: PagingState<Int, CharacterDTO>): Int? {
        return state.anchorPosition?.let { anchorPosition ->
            state.closestPageToPosition(anchorPosition)?.prevKey?.plus(1)
                ?: state.closestPageToPosition(anchorPosition)?.nextKey?.minus(1)
        }
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, CharacterDTO> {
        return try {
            val currentPage = params.key ?: 1
            val response: Response<CharacterResponse> = apiService.getCharacters(
                page = currentPage,
                name = name,
                status = status,
                species = species,
                type = type,
                gender = gender
            )
            responseData = response.body()?.characters ?: emptyList()  // TODO fix this
            Log.d("TAG", " page $currentPage responseData = $responseData")
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