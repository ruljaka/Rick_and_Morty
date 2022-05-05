package com.ruslangrigoriev.rickandmorty.domain.useCases.episodes

import com.ruslangrigoriev.rickandmorty.domain.repository.EpisodesRepository
import javax.inject.Inject

class GetEpisodeByIdUseCase @Inject constructor(
    private val episodesRepository: EpisodesRepository
) {
    suspend operator fun invoke(episodeID: Int) =
        episodesRepository.getEpisodeById(episodeID)
}