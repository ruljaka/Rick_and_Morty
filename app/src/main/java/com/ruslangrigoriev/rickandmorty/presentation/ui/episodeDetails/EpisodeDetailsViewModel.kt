package com.ruslangrigoriev.rickandmorty.presentation.ui.episodeDetails

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ruslangrigoriev.rickandmorty.domain.entity.characters.Character
import com.ruslangrigoriev.rickandmorty.domain.entity.episodes.Episode
import com.ruslangrigoriev.rickandmorty.domain.useCases.episodes.GetEpisodeByIdUseCase
import com.ruslangrigoriev.rickandmorty.domain.useCases.episodes.GetEpisodeCharactersUseCase
import com.ruslangrigoriev.rickandmorty.presentation.common.toListIds
import com.ruslangrigoriev.rickandmorty.presentation.mappers.Mapper
import com.ruslangrigoriev.rickandmorty.presentation.model.EpisodeModel
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.launch
import javax.inject.Inject

class EpisodeDetailsViewModel @Inject constructor(
    private val getEpisodeByIdUseCase: GetEpisodeByIdUseCase,
    private val getEpisodeCharactersUseCase: GetEpisodeCharactersUseCase,
    private val episodeMapper: Mapper<@JvmSuppressWildcards Episode, List<@JvmSuppressWildcards Character>, @JvmSuppressWildcards EpisodeModel>
) : ViewModel() {

    private val _loading: MutableLiveData<Boolean> = MutableLiveData()
    val loading: LiveData<Boolean> = _loading

    private val _data: MutableLiveData<EpisodeModel> = MutableLiveData()
    val data: LiveData<EpisodeModel> = _data

    private val _error: MutableLiveData<String?> = MutableLiveData()
    val error: LiveData<String?> = _error

    private val exceptionHandler = CoroutineExceptionHandler { _, throwable ->
        Log.e(EpisodeDetailsViewModel::class.simpleName, throwable.message ?: "Unknown error")
        _error.postValue("Something went wrong \nTry refresh")
        _loading.postValue(false)
    }

    fun fetchEpisode(episodeID: Int) {
        _error.value = null
        _loading.value = true
        viewModelScope.launch(exceptionHandler) {
            val episode = getEpisodeByIdUseCase(episodeID)
            episode?.let {
                val charactersIds = episode.characters.toListIds()
                val characters = getEpisodeCharactersUseCase(charactersIds)
                val episodeModel = episodeMapper.map(episode, characters ?: emptyList())
                _data.postValue(episodeModel)
                _loading.postValue(false)
            } ?: run {
                _error.postValue("Not found \nCheck internet connection and try refresh")
                _loading.postValue(false)
            }
        }
    }
}