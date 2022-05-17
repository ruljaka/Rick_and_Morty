package com.ruslangrigoriev.rickandmorty.presentation.mappers

import com.ruslangrigoriev.rickandmorty.data.dto_and_entity.characters.Character
import com.ruslangrigoriev.rickandmorty.data.dto_and_entity.episodes.Episode
import com.ruslangrigoriev.rickandmorty.presentation.model.EpisodeModel
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