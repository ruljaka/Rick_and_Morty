package com.ruslangrigoriev.rickandmorty

import com.ruslangrigoriev.rickandmorty.presentation.mappers.CharacterMapper
import org.hamcrest.CoreMatchers.equalTo
import org.hamcrest.MatcherAssert.assertThat
import org.junit.Test

class CharacterMapperTest {
    private val testPOJO = TestPOJO()
    private val character = testPOJO.character
    private val episodesList = testPOJO.episodesList
    private val testCharacterModel = testPOJO.characterModel

    @Test
    fun ` should map character to characterModel`() {
        val mapper = CharacterMapper()
        val actualCharacterModel = mapper.map(character, episodesList)
        assertThat(actualCharacterModel, equalTo(testCharacterModel))
    }
}