package com.ruslangrigoriev.rickandmorty.domain.useCases

import com.ruslangrigoriev.rickandmorty.domain.repository.CharacterRepository
import javax.inject.Inject

class GetAllCharactersUseCase @Inject constructor(
    private val characterRepository: CharacterRepository
) {
    operator fun invoke() = characterRepository.getAllCharacters()
}