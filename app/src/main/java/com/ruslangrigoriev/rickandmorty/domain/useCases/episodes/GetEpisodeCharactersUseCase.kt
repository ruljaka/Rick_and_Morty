package com.ruslangrigoriev.rickandmorty.domain.useCases.episodes

import com.ruslangrigoriev.rickandmorty.domain.repository.EpisodesRepository
import javax.inject.Inject

class GetEpisodeCharactersUseCase @Inject constructor(
    private val episodesRepository: EpisodesRepository
) {
    suspend operator fun invoke(ids: List<Int>) =
        episodesRepository.getEpisodeCharacters(ids)
}