package com.ruslangrigoriev.rickandmorty.presentation.mappers

import com.ruslangrigoriev.rickandmorty.data.dto_and_entity.characterDTO.CharacterDTO
import com.ruslangrigoriev.rickandmorty.data.dto_and_entity.locationDTO.LocationDTO
import com.ruslangrigoriev.rickandmorty.presentation.model.LocationModel

class LocationMapper : Mapper<LocationDTO, List<CharacterDTO>, LocationModel> {
    override fun map(input: LocationDTO, list: List<CharacterDTO>): LocationModel {
        return LocationModel(
            id = input.id,
            name = input.name,
            type = input.type,
            dimension = input.dimension,
            residents = list,
        )
    }
}