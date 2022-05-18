package com.ruslangrigoriev.rickandmorty

import com.ruslangrigoriev.rickandmorty.presentation.mappers.CharacterMapper
import com.ruslangrigoriev.rickandmorty.presentation.model.CharacterModel
import org.hamcrest.CoreMatchers.equalTo
import org.hamcrest.MatcherAssert.assertThat
import org.junit.Test

class CharacterMapperTest {
    private val testPOJO = TestPOJO()
    private val character = testPOJO.character
    private val episodesList = testPOJO.episodesList

    private val testCharacterModel = CharacterModel(
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

    @Test
    fun ` should map character to characterModel`() {
        val mapper = CharacterMapper()
        val actualCharacterModel = mapper.map(character, episodesList)
        assertThat(actualCharacterModel, equalTo(testCharacterModel))
    }

}