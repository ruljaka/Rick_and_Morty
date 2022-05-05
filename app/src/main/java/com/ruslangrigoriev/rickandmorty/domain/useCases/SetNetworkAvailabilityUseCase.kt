package com.ruslangrigoriev.rickandmorty.domain.useCases

import com.ruslangrigoriev.rickandmorty.domain.repository.CharacterRepository
import javax.inject.Inject

class SetNetworkAvailabilityUseCase @Inject constructor(
    private val characterRepository: CharacterRepository
) {
    operator fun invoke(status: Boolean) {
        characterRepository.setNetworkStatus(status)
    }
}