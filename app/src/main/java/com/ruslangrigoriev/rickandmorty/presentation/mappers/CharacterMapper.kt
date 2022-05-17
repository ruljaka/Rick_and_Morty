package com.ruslangrigoriev.rickandmorty.presentation.mappers

import com.ruslangrigoriev.rickandmorty.data.dto_and_entity.characters.Character
import com.ruslangrigoriev.rickandmorty.data.dto_and_entity.episodes.Episode
import com.ruslangrigoriev.rickandmorty.presentation.common.getId
import com.ruslangrigoriev.rickandmorty.presentation.model.CharacterModel
import javax.inject.Inject

class CharacterMapper @Inject constructor() :
    Mapper<Character, List<Episode>, CharacterModel> {
    override fun map(input: Character, list: List<Episode>): CharacterModel {
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