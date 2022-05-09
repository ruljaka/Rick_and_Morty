package com.ruslangrigoriev.rickandmorty.presentation.ui.locations

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.ruslangrigoriev.rickandmorty.data.dto.locationDTO.LocationDTO
import com.ruslangrigoriev.rickandmorty.domain.useCases.locations.GetLocationsUseCase
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class LocationsViewModel @Inject constructor(
    private val getLocationsUseCase: GetLocationsUseCase
) : ViewModel() {

    private var _locationsFlow: Flow<PagingData<LocationDTO>>? = null
    val locationsFlow: Flow<PagingData<LocationDTO>>?
        get() = _locationsFlow

    init {
         getLocations()
    }

    fun getLocations(filter: LocationsFilter? = null) {
        _locationsFlow = getLocationsUseCase(
            name = filter?.name,
            type = filter?.type,
            dimension = filter?.dimension,
        ).cachedIn(viewModelScope)
    }
}