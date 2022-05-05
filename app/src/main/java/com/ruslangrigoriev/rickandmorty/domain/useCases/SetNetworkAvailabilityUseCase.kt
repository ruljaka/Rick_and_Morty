package com.ruslangrigoriev.rickandmorty.domain.useCases

import com.ruslangrigoriev.rickandmorty.domain.repository.CharactersRepository
import com.ruslangrigoriev.rickandmorty.domain.repository.EpisodesRepository
import javax.inject.Inject

class SetNetworkAvailabilityUseCase @Inject constructor(
    private val charactersRepository: CharactersRepository,
    private val episodesRepository: EpisodesRepository,

) {
    operator fun invoke(status: Boolean) {
        charactersRepository.setNetworkStatus(status)
        episodesRepository.setNetworkStatus(status)
    }
}