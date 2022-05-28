package com.ruslangrigoriev.rickandmorty.episodes.domain.useCases

import com.ruslangrigoriev.rickandmorty.episodes.domain.EpisodesRepository
import javax.inject.Inject

class GetEpisodeByIdUseCase @Inject constructor(
    private val episodesRepository: EpisodesRepository
) {
    suspend operator fun invoke(episodeID: Int) =
        episodesRepository.getEpisodeById(episodeID)
}