package com.ruslangrigoriev.rickandmorty.presentation.characterDetails

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ruslangrigoriev.rickandmorty.common.toListIds
import com.ruslangrigoriev.rickandmorty.domain.mappers.CharacterMapper
import com.ruslangrigoriev.rickandmorty.domain.model.CharacterModel
import com.ruslangrigoriev.rickandmorty.domain.useCases.characters.GetCharacterByIdUseCase
import com.ruslangrigoriev.rickandmorty.domain.useCases.characters.GetCharacterEpisodesUseCase
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.launch
import javax.inject.Inject

class CharacterDetailsViewModel @Inject constructor(
    private val getCharacterByIdUseCase: GetCharacterByIdUseCase,
    private val getCharacterEpisodesUseCase: GetCharacterEpisodesUseCase
) : ViewModel() {

    private val _loading: MutableLiveData<Boolean> = MutableLiveData()
    val loading: LiveData<Boolean> = _loading

    private val _data: MutableLiveData<CharacterModel> = MutableLiveData()
    val data: LiveData<CharacterModel> = _data

    private val _error: MutableLiveData<String?> = MutableLiveData()
    val error: LiveData<String?> = _error

    private val exceptionHandler = CoroutineExceptionHandler { _, throwable ->
        Log.e(CharacterDetailsViewModel::class.simpleName, throwable.message ?: "Unknown error")
        _error.postValue("Something went wrong \nTry refresh")
        _loading.postValue(false)
    }

    fun fetchCharacter(characterID: Int) {
        _error.value = null
        _loading.value = true
        viewModelScope.launch(exceptionHandler) {
            val characterDTO = getCharacterByIdUseCase(characterID)
            characterDTO?.let {
                val episodesIds = characterDTO.episode.toListIds()
                val episodes = getCharacterEpisodesUseCase(episodesIds)
                val characterModel = CharacterMapper.map(characterDTO, episodes ?: emptyList())
                _data.postValue(characterModel)
                _loading.postValue(false)
            } ?: run {
                _error.postValue("Not found \nCheck internet connection and try refresh")
                _loading.postValue(false)
            }
        }
    }

}