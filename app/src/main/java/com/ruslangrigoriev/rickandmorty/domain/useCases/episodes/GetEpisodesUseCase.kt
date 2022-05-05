package com.ruslangrigoriev.rickandmorty.domain.useCases.episodes

import com.ruslangrigoriev.rickandmorty.domain.repository.EpisodesRepository
import javax.inject.Inject

class GetEpisodesUseCase @Inject constructor(
    private val episodesRepository: EpisodesRepository
) {
    operator fun invoke(
        name: String? = null,
        episode: String? = null,
    ) = episodesRepository.getEpisodes(
        name = name,
        episode = episode,
    )
}