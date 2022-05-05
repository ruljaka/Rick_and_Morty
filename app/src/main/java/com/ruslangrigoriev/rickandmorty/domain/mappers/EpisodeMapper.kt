package com.ruslangrigoriev.rickandmorty.domain.mappers

import com.ruslangrigoriev.rickandmorty.data.dto.characterDTO.CharacterDTO
import com.ruslangrigoriev.rickandmorty.data.dto.episodeDTO.EpisodeDTO
import com.ruslangrigoriev.rickandmorty.domain.model.EpisodeModel

object EpisodeMapper : Mapper<EpisodeDTO, List<CharacterDTO>, EpisodeModel> {
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