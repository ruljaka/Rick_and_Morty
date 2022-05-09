package com.ruslangrigoriev.rickandmorty.presentation.ui.locationDetails

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ruslangrigoriev.rickandmorty.presentation.mappers.LocationMapper
import com.ruslangrigoriev.rickandmorty.presentation.model.LocationModel
import com.ruslangrigoriev.rickandmorty.domain.useCases.locations.GetLocationByIdUseCase
import com.ruslangrigoriev.rickandmorty.domain.useCases.locations.GetLocationResidentsUseCase
import com.ruslangrigoriev.rickandmorty.presentation.common.toListIds
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.launch
import javax.inject.Inject

class LocationDetailsViewModel @Inject constructor(
    private val getLocationByIdUseCase: GetLocationByIdUseCase,
    private val getLocationResidentsUseCase: GetLocationResidentsUseCase,
    private val locationMapper: LocationMapper
) : ViewModel() {

    private val _loading: MutableLiveData<Boolean> = MutableLiveData()
    val loading: LiveData<Boolean> = _loading

    private val _data: MutableLiveData<LocationModel> = MutableLiveData()
    val data: LiveData<LocationModel> = _data

    private val _error: MutableLiveData<String?> = MutableLiveData()
    val error: LiveData<String?> = _error

    private val exceptionHandler = CoroutineExceptionHandler { _, throwable ->
        Log.e(LocationDetailsViewModel::class.simpleName, throwable.message ?: "Unknown error")
        _error.postValue("Something went wrong \nTry refresh")
        _loading.postValue(false)
    }

    fun fetchLocation(locationID: Int) {
        _error.value = null
        _loading.value = true
        viewModelScope.launch(exceptionHandler) {
            val locationDTO = getLocationByIdUseCase(locationID)
            locationDTO?.let {
                val residentsIds = locationDTO.residents.toListIds()
                val residents = getLocationResidentsUseCase(residentsIds)
                val locationModel = locationMapper.map(locationDTO, residents ?: emptyList())
                _data.postValue(locationModel)
                _loading.postValue(false)
            } ?: run {
                _error.postValue("Not found \nCheck internet connection and try refresh")
                _loading.postValue(false)
            }
        }
    }
}