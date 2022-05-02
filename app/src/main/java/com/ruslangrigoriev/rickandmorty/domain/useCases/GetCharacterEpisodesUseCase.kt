package com.ruslangrigoriev.rickandmorty.domain.useCases

import com.ruslangrigoriev.rickandmorty.domain.repository.CharacterRepository
import javax.inject.Inject

class GetCharacterEpisodesUseCase @Inject constructor(
    private val characterRepository: CharacterRepository
) {
    suspend operator fun invoke(ids: String) =
        characterRepository.getCharacterEpisodes(ids)
}