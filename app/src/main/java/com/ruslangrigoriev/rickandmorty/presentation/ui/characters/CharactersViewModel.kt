package com.ruslangrigoriev.rickandmorty.presentation.ui.characters

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.ruslangrigoriev.rickandmorty.data.dto_and_entity.characterDTO.CharacterDTO
import com.ruslangrigoriev.rickandmorty.domain.useCases.characters.GetCharactersUseCase
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class CharactersViewModel @Inject constructor(
    private val getCharactersUseCase: GetCharactersUseCase
) : ViewModel() {

    private var _charactersFlow: Flow<PagingData<CharacterDTO>>? = null
    val charactersFlow: Flow<PagingData<CharacterDTO>>?
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