package com.ruslangrigoriev.rickandmorty

import com.ruslangrigoriev.rickandmorty.characters.data.local.CharactersDao
import com.ruslangrigoriev.rickandmorty.characters.data.remote.CharactersService
import com.ruslangrigoriev.rickandmorty.characters.data.repository.CharactersRepositoryImpl
import com.ruslangrigoriev.rickandmorty.episodes.data.local.EpisodesDao
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.runBlocking
import org.hamcrest.CoreMatchers.equalTo
import org.hamcrest.MatcherAssert.assertThat
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test
import retrofit2.Response
import java.lang.reflect.Field


class CharactersRepositoryImplTest {

    private val testPOJO = TestPOJO()
    private val testCharacter = testPOJO.character
    private val testEpisodesList = testPOJO.episodesList
    private val testID = 1
    private val testIds = listOf(1, 2)

    @ExperimentalCoroutinesApi

    private lateinit var charactersService: CharactersService
    private lateinit var charactersDao: CharactersDao

    private lateinit var sut: CharactersRepositoryImpl
    private val privateIsNetworkAvailableField: Field =
        CharactersRepositoryImpl::class.java.getDeclaredField("isNetworkAvailable")

    @ExperimentalCoroutinesApi
    @Before
    fun setUp() {
        charactersService = mockk()
        coEvery { charactersService.getCharacterById(any()) } returns Response.success(testCharacter)
        coEvery { charactersService.getCharacterEpisodes(any()) } returns Response.success(
            testEpisodesList
        )

        charactersDao = mockk()
        coEvery { charactersDao.getCharacterById(any()) } returns testCharacter
        coEvery { charactersDao.insertCharacter(any()) } returns Unit
        coEvery { charactersDao.getListEpisodesByIds(any()) } returns testEpisodesList
        coEvery { charactersDao.insertEpisodes(any()) } returns Unit

        sut =
            CharactersRepositoryImpl(charactersService, charactersDao)
        privateIsNetworkAvailableField.isAccessible = true

    }

    @Test
    fun `should get character by id when network available`() {
        runBlocking {
            sut.setNetworkStatus(true)
            val actualCharacter = sut.getCharacterById(testID)
            assertThat(actualCharacter, equalTo(testCharacter))
        }
    }

    @Test
    fun `should get character by id when network not available`() {
        runBlocking {
            sut.setNetworkStatus(false)
            val actualCharacter = sut.getCharacterById(testID)
            assertThat(actualCharacter, equalTo(testCharacter))
        }
    }

    @Test
    fun `should get Character Episodes when network available`() {
        runBlocking {
            sut.setNetworkStatus(true)
            val actualEpisodeList = sut.getCharacterEpisodes(testIds)
            assertThat(actualEpisodeList, equalTo(testEpisodesList))
        }
    }

    @Test
    fun `should get Character Episodes when network not available`() {
        runBlocking {
            sut.setNetworkStatus(false)
            val actualEpisodeList = sut.getCharacterEpisodes(testIds)
            assertThat(actualEpisodeList, equalTo(testEpisodesList))
        }
    }

    @Test
    fun `should change network status to available`() {
        sut.setNetworkStatus(true)
        val isNetworkAvailable = privateIsNetworkAvailableField.get(sut) as Boolean
        assertTrue(isNetworkAvailable)
    }

}