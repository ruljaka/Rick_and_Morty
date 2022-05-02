package com.ruslangrigoriev.rickandmorty.presentation.characters

import androidx.lifecycle.*
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.ruslangrigoriev.rickandmorty.data.dto.characterDTO.CharacterDTO
import com.ruslangrigoriev.rickandmorty.domain.useCases.GetAllCharactersUseCase
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class CharactersViewModel @Inject constructor(
    private val getAllCharactersUseCase: GetAllCharactersUseCase
) : ViewModel() {

//    private val _result = getAllCharactersUseCase()
//        .asLiveData(viewModelScope.coroutineContext)
//    val result: LiveData<PagingData<Character>>
//        get() = _result

    fun getAllCharacters(): Flow<PagingData<CharacterDTO>> {
        return getAllCharactersUseCase().cachedIn(viewModelScope)
    }

}