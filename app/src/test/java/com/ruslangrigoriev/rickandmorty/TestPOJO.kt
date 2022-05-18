package com.ruslangrigoriev.rickandmorty

import com.ruslangrigoriev.rickandmorty.data.dto_and_entity.characters.Character
import com.ruslangrigoriev.rickandmorty.data.dto_and_entity.characters.Location
import com.ruslangrigoriev.rickandmorty.data.dto_and_entity.characters.Origin
import com.ruslangrigoriev.rickandmorty.data.dto_and_entity.episodes.Episode
import com.ruslangrigoriev.rickandmorty.presentation.model.CharacterModel

class TestPOJO {

    val character = Character(
        id = 2,
        name = "Morty Smith",
        status = "Alive",
        species = "Human",
        type = "",
        gender = "Male",
        origin = Origin(name = "Earth", url = "https://rickandmortyapi.com/api/location/1"),
        location = Location(name = "Earth", url = "https://rickandmortyapi.com/api/location/20"),
        image = "https://rickandmortyapi.com/api/character/avatar/2.jpeg",
        episode = listOf(
            "https://rickandmortyapi.com/api/episode/1",
            "https://rickandmortyapi.com/api/episode/2",
        ),
        url = "https://rickandmortyapi.com/api/character/2",
        created = "2017-11-04T18:50:21.651Z"
    )

    val episodesList = listOf(
        Episode(
            id = 28,
            name = "The Ricklantis Mixup",
            airDate = "September 10, 2017",
            episode = "S03E07",
            characters = listOf(
                "https://rickandmortyapi.com/api/character/1",
                "https://rickandmortyapi.com/api/character/2",
            ),
            url = "https://rickandmortyapi.com/api/episode/28",
            created = "2017-11-10T12:56:36.618Z"
        )
    )

     val characterModel = CharacterModel(
        id = 2,
        name = "Morty Smith",
        status = "Alive",
        species = "Human",
        type = "",
        gender = "Male",
        originName = "Earth",
        originID = 1,
        locationName = "Earth",
        locationID = 20,
        image = "https://rickandmortyapi.com/api/character/avatar/2.jpeg",
        episodes = episodesList
    )
}