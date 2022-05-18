package com.ruslangrigoriev.rickandmorty

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.ruslangrigoriev.rickandmorty.domain.useCases.characters.GetCharacterByIdUseCase
import com.ruslangrigoriev.rickandmorty.domain.useCases.characters.GetCharacterEpisodesUseCase
import com.ruslangrigoriev.rickandmorty.presentation.mappers.CharacterMapper
import com.ruslangrigoriev.rickandmorty.presentation.ui.characterDetails.CharacterDetailsViewModel
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.hamcrest.CoreMatchers.equalTo
import org.hamcrest.MatcherAssert.assertThat
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test


class CharacterDetailsViewModelTest {

    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    @ExperimentalCoroutinesApi
    private val dispatcher = StandardTestDispatcher()

    private val getCharacterByIdUseCase: GetCharacterByIdUseCase = mockk()
    private val getCharacterEpisodesUseCase: GetCharacterEpisodesUseCase = mockk()
    private val characterMapper = CharacterMapper()

    private val viewModel = CharacterDetailsViewModel(
        getCharacterByIdUseCase,
        getCharacterEpisodesUseCase,
        characterMapper
    )

    private val testPOJO = TestPOJO()
    private val testCharacter = testPOJO.character
    private val testEpisodesList = testPOJO.episodesList
    private val testCharacterModel = testPOJO.characterModel
    private val testID = 1

    @ExperimentalCoroutinesApi
    @Before
    fun setUP() {
        Dispatchers.setMain(dispatcher)
        coEvery { getCharacterByIdUseCase(any()) } returns testCharacter
        coEvery { getCharacterEpisodesUseCase(any()) } returns testEpisodesList
    }

    @ExperimentalCoroutinesApi
    @Test
    fun `should update data`() {
        runTest {
            viewModel.fetchCharacter(testID)
        }
        val actual = viewModel.data.value
        assertThat(actual, equalTo(testCharacterModel))
    }

    @ExperimentalCoroutinesApi
    @After
    fun cleanup() {
        Dispatchers.resetMain()
    }
}