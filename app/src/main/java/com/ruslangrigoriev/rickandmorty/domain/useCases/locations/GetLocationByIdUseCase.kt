package com.ruslangrigoriev.rickandmorty.domain.useCases.locations

import com.ruslangrigoriev.rickandmorty.domain.repository.LocationsRepository
import javax.inject.Inject

class GetLocationByIdUseCase @Inject constructor(
    private val locationsRepository: LocationsRepository
) {
    suspend operator fun invoke(locationID: Int) =
        locationsRepository.getLocationById(locationID)
}