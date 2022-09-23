package com.ruslangrigoriev.rickandmorty.presentation.mappers

import com.ruslangrigoriev.rickandmorty.domain.entity.characters.Character
import com.ruslangrigoriev.rickandmorty.domain.entity.episodes.Episode
import com.ruslangrigoriev.rickandmorty.presentation.model.CharacterModel
import javax.inject.Inject

class CharacterMapper @Inject constructor() :
    Mapper<@JvmSuppressWildcards Character,  List<@JvmSuppressWildcards Episode>, @JvmSuppressWildcards CharacterModel> {
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