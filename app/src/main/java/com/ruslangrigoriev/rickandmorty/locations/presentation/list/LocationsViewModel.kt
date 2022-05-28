package com.ruslangrigoriev.rickandmorty.locations.presentation.list

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.ruslangrigoriev.rickandmorty.core.data.dto.location_dto.Location
import com.ruslangrigoriev.rickandmorty.locations.domain.useCases.GetLocationsUseCase
import com.ruslangrigoriev.rickandmorty.locations.presentation.list.filter.LocationsFilter
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class LocationsViewModel @Inject constructor(
    private val getLocationsUseCase: GetLocationsUseCase
) : ViewModel() {

    private var _locationsFlow: Flow<PagingData<Location>>? = null
    val locationsFlow: Flow<PagingData<Location>>?
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