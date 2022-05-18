package com.ruslangrigoriev.rickandmorty.domain.useCases

import com.ruslangrigoriev.rickandmorty.domain.repository.CharactersRepository
import com.ruslangrigoriev.rickandmorty.domain.repository.EpisodesRepository
import com.ruslangrigoriev.rickandmorty.domain.repository.LocationsRepository
import javax.inject.Inject

class SetNetworkAvailabilityUseCase @Inject constructor(
    private val charactersRepository: CharactersRepository,
    private val episodesRepository: EpisodesRepository,
    private val locationsRepository: LocationsRepository
) {
    operator fun invoke(status: Boolean) {
        charactersRepository.setNetworkStatus(status)
        episodesRepository.setNetworkStatus(status)
        locationsRepository.setNetworkStatus(status)
    }
}