package com.ruslangrigoriev.rickandmorty.episodes.domain.useCases

import com.ruslangrigoriev.rickandmorty.episodes.domain.EpisodesRepository
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