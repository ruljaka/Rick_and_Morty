package com.ruslangrigoriev.rickandmorty.episodes.presentation.details.mapper

import com.ruslangrigoriev.rickandmorty.core.data.dto.character_dto.Character
import com.ruslangrigoriev.rickandmorty.core.data.dto.episode_dto.Episode
import com.ruslangrigoriev.rickandmorty.core.presentation.common.Mapper
import com.ruslangrigoriev.rickandmorty.episodes.presentation.details.model.EpisodeModel
import javax.inject.Inject

class EpisodeMapper @Inject constructor() : Mapper<Episode, List<Character>, EpisodeModel> {
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