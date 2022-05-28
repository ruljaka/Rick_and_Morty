package com.ruslangrigoriev.rickandmorty.characters.presentation.details

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ruslangrigoriev.rickandmorty.characters.presentation.details.mapper.CharacterMapper
import com.ruslangrigoriev.rickandmorty.characters.presentation.details.model.CharacterModel
import com.ruslangrigoriev.rickandmorty.characters.domain.useCases.GetCharacterByIdUseCase
import com.ruslangrigoriev.rickandmorty.characters.domain.useCases.GetCharacterEpisodesUseCase
import com.ruslangrigoriev.rickandmorty.core.presentation.common.toListIds
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.launch
import javax.inject.Inject

class CharacterDetailsViewModel @Inject constructor(
    private val getCharacterByIdUseCase: GetCharacterByIdUseCase,
    private val getCharacterEpisodesUseCase: GetCharacterEpisodesUseCase,
    private val characterMapper: CharacterMapper
) : ViewModel() {

    private val _loading: MutableLiveData<Boolean> = MutableLiveData()
    val loading: LiveData<Boolean> = _loading

    private val _data: MutableLiveData<CharacterModel> = MutableLiveData()
    val data: LiveData<CharacterModel> = _data

    private val _error: MutableLiveData<String?> = MutableLiveData()
    val error: LiveData<String?> = _error

    private val exceptionHandler = CoroutineExceptionHandler { _, throwable ->
        Log.e(CharacterDetailsViewModel::class.simpleName, throwable.message ?: "Unknown error")
        _loading.postValue(false)
        _error.postValue("Failed to load data \nTry refresh")
    }

    fun fetchCharacter(characterID: Int) {
        _error.value = null
        _loading.value = true
        viewModelScope.launch(exceptionHandler) {
            val character = getCharacterByIdUseCase(characterID)
            character?.let {
                val episodesIds = character.episode.toListIds()
                val episodes = getCharacterEpisodesUseCase(episodesIds)
                val characterModel = characterMapper.map(character, episodes ?: emptyList())
                _data.postValue(characterModel)
                _loading.postValue(false)
            } ?: run {
                _loading.postValue(false)
                _error.postValue("Not found \nCheck internet connection and try refresh")
            }
        }
    }

}