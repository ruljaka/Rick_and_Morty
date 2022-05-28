package com.ruslangrigoriev.rickandmorty.locations.domain.useCases

import com.ruslangrigoriev.rickandmorty.locations.domain.LocationsRepository
import javax.inject.Inject

class GetLocationsUseCase @Inject constructor(
    private val locationsRepository: LocationsRepository
) {
    operator fun invoke(
        name: String? = null,
        type: String? = null,
        dimension: String? = null,
    ) = locationsRepository.getLocations(
        name = name,
        type = type,
        dimension = dimension,
    )
}