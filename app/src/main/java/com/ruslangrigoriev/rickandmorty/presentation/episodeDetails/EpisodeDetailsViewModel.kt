package com.ruslangrigoriev.rickandmorty.presentation.episodeDetails

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ruslangrigoriev.rickandmorty.common.toListIds
import com.ruslangrigoriev.rickandmorty.domain.mappers.EpisodeMapper
import com.ruslangrigoriev.rickandmorty.domain.model.EpisodeModel
import com.ruslangrigoriev.rickandmorty.domain.useCases.episodes.GetEpisodeByIdUseCase
import com.ruslangrigoriev.rickandmorty.domain.useCases.episodes.GetEpisodeCharactersUseCase
import com.ruslangrigoriev.rickandmorty.presentation.RequestState
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.launch
import javax.inject.Inject

class EpisodeDetailsViewModel @Inject constructor(
    private val getEpisodeByIdUseCase: GetEpisodeByIdUseCase,
    private val getEpisodeCharactersUseCase: GetEpisodeCharactersUseCase
) : ViewModel() {

    private val _requestState: MutableLiveData<RequestState<EpisodeModel>> = MutableLiveData()
    val requestState: LiveData<RequestState<EpisodeModel>> = _requestState

    private val exceptionHandler = CoroutineExceptionHandler { _, throwable ->
        _requestState.postValue(RequestState.Error(throwable.message ?: "Unknown Error"))
    }

    fun fetchCharacter(episodeID: Int) {
        _requestState.value = RequestState.Loading()
        viewModelScope.launch(exceptionHandler) {
            val episodeDTO = getEpisodeByIdUseCase(episodeID)
            val charactersIds = episodeDTO.characters.toListIds()
            val characters = getEpisodeCharactersUseCase(charactersIds)
            val episodeModel = EpisodeMapper.map(episodeDTO, characters ?: emptyList())
            _requestState.postValue(RequestState.Success(data = episodeModel))
        }
    }
}