package com.ruslangrigoriev.rickandmorty.domain.mappers

import com.ruslangrigoriev.rickandmorty.data.dto.characterDTO.CharacterDTO
import com.ruslangrigoriev.rickandmorty.data.dto.episodeDTO.EpisodeDTO
import com.ruslangrigoriev.rickandmorty.domain.model.CharacterModel
import com.ruslangrigoriev.rickandmorty.presentation.common.getId

object CharacterMapper : Mapper<CharacterDTO, List<EpisodeDTO>, CharacterModel> {
    override fun map(input: CharacterDTO, list: List<EpisodeDTO>): CharacterModel {
        return CharacterModel(
            id = input.id,
            status = input.status,
            name = input.name,
            species = input.species,
            gender = input.gender,
            type = input.type,
            image = input.image,
            episodes = list,
            locationName = input.location.name,
            locationID = input.location.url.getId(),
            originName = input.origin.name,
            originID = input.origin.url.getId()
        )
    }
}