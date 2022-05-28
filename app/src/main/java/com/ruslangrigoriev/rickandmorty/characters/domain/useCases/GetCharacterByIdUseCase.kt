package com.ruslangrigoriev.rickandmorty.characters.domain.useCases

import com.ruslangrigoriev.rickandmorty.characters.domain.CharactersRepository
import javax.inject.Inject

class GetCharacterByIdUseCase @Inject constructor(
    private val charactersRepository: CharactersRepository
) {
    suspend operator fun invoke(characterID: Int) =
        charactersRepository.getCharacterById(characterID)
}