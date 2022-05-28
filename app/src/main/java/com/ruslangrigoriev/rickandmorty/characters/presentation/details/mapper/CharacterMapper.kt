package com.ruslangrigoriev.rickandmorty.characters.presentation.details.mapper

import com.ruslangrigoriev.rickandmorty.core.data.dto.character_dto.Character
import com.ruslangrigoriev.rickandmorty.core.data.dto.episode_dto.Episode
import com.ruslangrigoriev.rickandmorty.core.presentation.common.Mapper
import com.ruslangrigoriev.rickandmorty.characters.presentation.details.model.CharacterModel
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
            locationID = getId(input.location.url),
            originName = input.origin.name,
            originID = getId(input.origin.url)
        )
    }

    private fun getId(url: String): Int? = if (url.isNotEmpty()) {
        url.replace("\"", "")
            .substringAfterLast('/').toInt()
    } else null
}