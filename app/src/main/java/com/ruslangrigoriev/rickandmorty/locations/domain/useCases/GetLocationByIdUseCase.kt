package com.ruslangrigoriev.rickandmorty.locations.domain.useCases

import com.ruslangrigoriev.rickandmorty.locations.domain.LocationsRepository
import javax.inject.Inject

class GetLocationByIdUseCase @Inject constructor(
    private val locationsRepository: LocationsRepository
) {
    suspend operator fun invoke(locationID: Int) =
        locationsRepository.getLocationById(locationID)
}