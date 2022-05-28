package com.ruslangrigoriev.rickandmorty.locations.presentation.details.mapper

import com.ruslangrigoriev.rickandmorty.core.data.dto.character_dto.Character
import com.ruslangrigoriev.rickandmorty.core.data.dto.location_dto.Location
import com.ruslangrigoriev.rickandmorty.core.presentation.common.Mapper
import com.ruslangrigoriev.rickandmorty.locations.presentation.details.model.LocationModel
import javax.inject.Inject

class LocationMapper @Inject constructor() :
    Mapper<Location, List<Character>, LocationModel> {
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