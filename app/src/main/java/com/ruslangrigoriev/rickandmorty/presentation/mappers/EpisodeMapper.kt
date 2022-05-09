package com.ruslangrigoriev.rickandmorty.presentation.mappers

import com.ruslangrigoriev.rickandmorty.data.dto.characterDTO.CharacterDTO
import com.ruslangrigoriev.rickandmorty.data.dto.episodeDTO.EpisodeDTO
import com.ruslangrigoriev.rickandmorty.presentation.model.EpisodeModel

class EpisodeMapper : Mapper<EpisodeDTO, List<CharacterDTO>, EpisodeModel> {
    override fun map(input: EpisodeDTO, list: List<CharacterDTO>): EpisodeModel {
        return EpisodeModel(
            id = input.id,
            name = input.name,
            episode = input.episode,
            airDate = input.airDate,
            characters = list,
        )
    }
}