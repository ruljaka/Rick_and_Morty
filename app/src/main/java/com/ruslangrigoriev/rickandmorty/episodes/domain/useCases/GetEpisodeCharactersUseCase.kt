package com.ruslangrigoriev.rickandmorty.episodes.domain.useCases

import com.ruslangrigoriev.rickandmorty.episodes.domain.EpisodesRepository
import javax.inject.Inject

class GetEpisodeCharactersUseCase @Inject constructor(
    private val episodesRepository: EpisodesRepository
) {
    suspend operator fun invoke(ids: List<Int>) =
        episodesRepository.getEpisodeCharacters(ids)
}