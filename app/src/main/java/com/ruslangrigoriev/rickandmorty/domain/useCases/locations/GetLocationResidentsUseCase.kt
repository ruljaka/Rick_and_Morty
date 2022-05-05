package com.ruslangrigoriev.rickandmorty.domain.useCases.locations

import com.ruslangrigoriev.rickandmorty.domain.repository.LocationsRepository
import javax.inject.Inject

class GetLocationResidentsUseCase @Inject constructor(
    private val locationsRepository: LocationsRepository
) {
    suspend operator fun invoke(ids: List<Int>) =
        locationsRepository.getLocationResidents(ids)
}