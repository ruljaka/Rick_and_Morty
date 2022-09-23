package com.ruslangrigoriev.rickandmorty.presentation.ui.locations

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.ruslangrigoriev.rickandmorty.domain.entity.locations.Location
import com.ruslangrigoriev.rickandmorty.domain.useCases.locations.GetLocationsUseCase
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