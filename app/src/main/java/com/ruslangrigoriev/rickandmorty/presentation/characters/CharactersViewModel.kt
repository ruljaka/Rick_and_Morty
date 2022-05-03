package com.ruslangrigoriev.rickandmorty.presentation.characters

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.ruslangrigoriev.rickandmorty.data.dto.characterDTO.CharacterDTO
import com.ruslangrigoriev.rickandmorty.domain.useCases.GetCharactersUseCase
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class CharactersViewModel @Inject constructor(
    private val getCharactersUseCase: GetCharactersUseCase
) : ViewModel() {

    var filter: CharactersFilter? = null

    private var _charactersFlow: Flow<PagingData<CharacterDTO>>? = null
    val charactersFlow: Flow<PagingData<CharacterDTO>>?
        get() = _charactersFlow

    init {
        if (charactersFlow == null) getCharacters()
    }

    fun getCharacters(
        filter: CharactersFilter? = null
    ) {
        this.filter = filter
        _charactersFlow = getCharactersUseCase(
            name = filter?.name,
            status = filter?.status,
            species = filter?.species,
            type = filter?.type,
            gender = filter?.gender
        ).cachedIn(viewModelScope)
    }

}