package com.ruslangrigoriev.rickandmorty.core.domain

import com.ruslangrigoriev.rickandmorty.characters.domain.CharactersRepository
import com.ruslangrigoriev.rickandmorty.episodes.domain.EpisodesRepository
import com.ruslangrigoriev.rickandmorty.locations.domain.LocationsRepository
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