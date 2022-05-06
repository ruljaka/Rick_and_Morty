package com.ruslangrigoriev.rickandmorty.presentation.locations

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

    var locationsFilter: LocationsFilter? = null

    private var _locationsFlow: Flow<PagingData<LocationDTO>>? = null
    val locationsFlow: Flow<PagingData<LocationDTO>>?
        get() = _locationsFlow

    init {
        if (locationsFlow == null) getLocations()
    }

    fun getLocations(filter: LocationsFilter? = null) {
        filter?.let { locationsFilter = filter }
        _locationsFlow = getLocationsUseCase(
            name = locationsFilter?.name,
            type = locationsFilter?.type,
            dimension = locationsFilter?.dimension,
        ).cachedIn(viewModelScope)
    }
}