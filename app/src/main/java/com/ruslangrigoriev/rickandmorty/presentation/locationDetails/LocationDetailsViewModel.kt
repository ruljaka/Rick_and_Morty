package com.ruslangrigoriev.rickandmorty.presentation.locationDetails

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ruslangrigoriev.rickandmorty.common.toListIds
import com.ruslangrigoriev.rickandmorty.domain.mappers.LocationMapper
import com.ruslangrigoriev.rickandmorty.domain.model.LocationModel
import com.ruslangrigoriev.rickandmorty.domain.useCases.locations.GetLocationByIdUseCase
import com.ruslangrigoriev.rickandmorty.domain.useCases.locations.GetLocationResidentsUseCase
import com.ruslangrigoriev.rickandmorty.presentation.RequestState
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.launch
import javax.inject.Inject

class LocationDetailsViewModel @Inject constructor(
    private val getLocationByIdUseCase: GetLocationByIdUseCase,
    private val getLocationResidentsUseCase: GetLocationResidentsUseCase
) : ViewModel() {

    private val _requestState: MutableLiveData<RequestState<LocationModel>> = MutableLiveData()
    val requestState: LiveData<RequestState<LocationModel>> = _requestState

    private val exceptionHandler = CoroutineExceptionHandler { _, throwable ->
        _requestState.postValue(RequestState.Error(throwable.message ?: "Unknown Error"))
    }

    fun fetchLocation(locationID: Int) {
        _requestState.value = RequestState.Loading()
        viewModelScope.launch() {
            val locationDTO = getLocationByIdUseCase(locationID)
            val residentsIds = locationDTO.residents.toListIds()
            val residents =
                if (residentsIds.isNotEmpty()) getLocationResidentsUseCase(residentsIds) else null
            val locationModel = LocationMapper.map(locationDTO, residents ?: emptyList())
            _requestState.postValue(RequestState.Success(data = locationModel))
        }
    }
}