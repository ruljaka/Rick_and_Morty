package com.ruslangrigoriev.rickandmorty.presentation.mappers

import com.ruslangrigoriev.rickandmorty.domain.entity.characters.Character
import com.ruslangrigoriev.rickandmorty.domain.entity.episodes.Episode
import com.ruslangrigoriev.rickandmorty.presentation.model.EpisodeModel
import javax.inject.Inject

class EpisodeMapper @Inject constructor() :
    Mapper<@JvmSuppressWildcards Episode, List<@JvmSuppressWildcards Character>, @JvmSuppressWildcards EpisodeModel> {
    override fun map(input: Episode, list: List<Character>): EpisodeModel {
        return EpisodeModel(
            id = input.id,
            name = input.name,
            episode = input.episode,
            airDate = input.airDate,
            characters = list,
        )
    }
}