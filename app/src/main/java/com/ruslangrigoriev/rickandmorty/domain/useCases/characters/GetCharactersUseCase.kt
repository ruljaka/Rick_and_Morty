package com.ruslangrigoriev.rickandmorty.domain.useCases.characters

import com.ruslangrigoriev.rickandmorty.domain.repository.CharactersRepository
import javax.inject.Inject


class GetCharactersUseCase @Inject constructor(
    private val charactersRepository: CharactersRepository
) {
    operator fun invoke(
        name: String? = null,
        status: String? = null,
        species: String? = null,
        type: String? = null,
        gender: String? = null
    ) = charactersRepository.getCharacters(
        name = name,
        status = status,
        species = species,
        type = type,
        gender = gender
    )
}