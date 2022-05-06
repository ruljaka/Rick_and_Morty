package com.ruslangrigoriev.rickandmorty.domain.mappers

import com.ruslangrigoriev.rickandmorty.data.dto.characterDTO.CharacterDTO
import com.ruslangrigoriev.rickandmorty.data.dto.locationDTO.LocationDTO
import com.ruslangrigoriev.rickandmorty.domain.model.LocationModel

object LocationMapper : Mapper<LocationDTO, List<CharacterDTO>, LocationModel> {
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