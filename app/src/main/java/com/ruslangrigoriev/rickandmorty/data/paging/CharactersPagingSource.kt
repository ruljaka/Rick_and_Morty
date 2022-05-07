package com.ruslangrigoriev.rickandmorty.data.paging

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.ruslangrigoriev.rickandmorty.data.dto.characterDTO.CharacterDTO
import com.ruslangrigoriev.rickandmorty.data.local.CharactersDao
import com.ruslangrigoriev.rickandmorty.data.remote.CharactersService
import com.ruslangrigoriev.rickandmorty.data.repository.getKey

class CharactersPagingSource(
    private val name: String? = null,
    private val status: String? = null,
    private val species: String? = null,
    private val type: String? = null,
    private val gender: String? = null,
    private val charactersService: CharactersService,
    private val charactersDao: CharactersDao
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
            val response = charactersService.getCharacters(
                page = currentPage,
                name = name,
                status = status,
                species = species,
                type = type,
                gender = gender
            ).body()
            responseData = response?.characters ?: emptyList()
            if (responseData.isNotEmpty()) {
                charactersDao.insertCharacters(responseData)
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