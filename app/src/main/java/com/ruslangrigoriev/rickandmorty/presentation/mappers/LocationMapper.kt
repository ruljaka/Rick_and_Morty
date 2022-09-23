package com.ruslangrigoriev.rickandmorty.presentation.mappers

import com.ruslangrigoriev.rickandmorty.domain.entity.characters.Character
import com.ruslangrigoriev.rickandmorty.domain.entity.locations.Location
import com.ruslangrigoriev.rickandmorty.presentation.model.LocationModel
import javax.inject.Inject

class LocationMapper @Inject constructor() :
    Mapper<@JvmSuppressWildcards Location, List<@JvmSuppressWildcards Character>, @JvmSuppressWildcards LocationModel> {
    override fun map(input: Location, list: List<Character>): LocationModel {
        return LocationModel(
            id = input.id,
            name = input.name,
            type = input.type,
            dimension = input.dimension,
            residents = list,
        )
    }
}