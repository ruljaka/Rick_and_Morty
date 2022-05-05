package com.ruslangrigoriev.rickandmorty.domain.useCases.characters

import com.ruslangrigoriev.rickandmorty.domain.repository.CharactersRepository
import javax.inject.Inject

class GetCharacterEpisodesUseCase @Inject constructor(
    private val charactersRepository: CharactersRepository
) {
    suspend operator fun invoke(ids: List<Int>) =
        charactersRepository.getCharacterEpisodes(ids)
}