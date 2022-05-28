package com.ruslangrigoriev.rickandmorty.characters.presentation.list

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.ruslangrigoriev.rickandmorty.core.data.dto.character_dto.Character
import com.ruslangrigoriev.rickandmorty.characters.domain.useCases.GetCharactersUseCase
import com.ruslangrigoriev.rickandmorty.characters.presentation.list.filter.CharactersFilter
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class CharactersViewModel @Inject constructor(
    private val getCharactersUseCase: GetCharactersUseCase
) : ViewModel() {

    private var _charactersFlow: Flow<PagingData<Character>>? = null
    val charactersFlow: Flow<PagingData<Character>>?
        get() = _charactersFlow

    init {
        getCharacters()
    }

    fun getCharacters(filter: CharactersFilter? = null) {
        _charactersFlow = getCharactersUseCase(
            name = filter?.name,
            status = filter?.status,
            species = filter?.species,
            type = filter?.type,
            gender = filter?.gender
        ).cachedIn(viewModelScope)
    }

}