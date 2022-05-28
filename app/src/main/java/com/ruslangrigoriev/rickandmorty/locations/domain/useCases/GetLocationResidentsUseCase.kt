package com.ruslangrigoriev.rickandmorty.locations.domain.useCases

import com.ruslangrigoriev.rickandmorty.locations.domain.LocationsRepository
import javax.inject.Inject

class GetLocationResidentsUseCase @Inject constructor(
    private val locationsRepository: LocationsRepository
) {
    suspend operator fun invoke(ids: List<Int>) =
        locationsRepository.getLocationResidents(ids)
}