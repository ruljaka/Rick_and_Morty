package com.ruslangrigoriev.rickandmorty.characters.domain.useCases

import com.ruslangrigoriev.rickandmorty.characters.domain.CharactersRepository
import javax.inject.Inject

class GetCharacterEpisodesUseCase @Inject constructor(
    private val charactersRepository: CharactersRepository
) {
    suspend operator fun invoke(ids: List<Int>) =
        charactersRepository.getCharacterEpisodes(ids)
}