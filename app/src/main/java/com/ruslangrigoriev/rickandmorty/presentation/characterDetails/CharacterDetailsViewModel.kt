package com.ruslangrigoriev.rickandmorty.presentation.characterDetails

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ruslangrigoriev.rickandmorty.common.toListIds
import com.ruslangrigoriev.rickandmorty.domain.mappers.CharacterMapper
import com.ruslangrigoriev.rickandmorty.domain.model.CharacterModel
import com.ruslangrigoriev.rickandmorty.domain.useCases.characters.GetCharacterByIdUseCase
import com.ruslangrigoriev.rickandmorty.domain.useCases.characters.GetCharacterEpisodesUseCase
import com.ruslangrigoriev.rickandmorty.presentation.common.RequestState
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.launch
import javax.inject.Inject

class CharacterDetailsViewModel @Inject constructor(
    private val getCharacterByIdUseCase: GetCharacterByIdUseCase,
    private val getCharacterEpisodesUseCase: GetCharacterEpisodesUseCase
) : ViewModel() {

    private val _requestState: MutableLiveData<RequestState<CharacterModel>> = MutableLiveData()
    val requestState: LiveData<RequestState<CharacterModel>> = _requestState

    private val exceptionHandler = CoroutineExceptionHandler { _, throwable ->
        _requestState.postValue(RequestState.Error(throwable.message ?: "Unknown Error"))
    }

    fun fetchCharacter(characterID: Int) {
        _requestState.value = RequestState.Loading()
        viewModelScope.launch(exceptionHandler) {
            val characterDTO = getCharacterByIdUseCase(characterID)
            val episodesIds = characterDTO.episode.toListIds()
            val episodes = getCharacterEpisodesUseCase(episodesIds)
            val characterModel = CharacterMapper.map(characterDTO, episodes ?: emptyList())
            _requestState.postValue(RequestState.Success(data = characterModel))
        }
    }
}