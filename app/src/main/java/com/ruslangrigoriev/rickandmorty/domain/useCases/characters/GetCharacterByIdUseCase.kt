package com.ruslangrigoriev.rickandmorty.domain.useCases.characters

import com.ruslangrigoriev.rickandmorty.domain.repository.CharactersRepository
import javax.inject.Inject

class GetCharacterByIdUseCase @Inject constructor(
    private val charactersRepository: CharactersRepository
) {
    suspend operator fun invoke(characterID: Int) =
        charactersRepository.getCharacterById(characterID)
}