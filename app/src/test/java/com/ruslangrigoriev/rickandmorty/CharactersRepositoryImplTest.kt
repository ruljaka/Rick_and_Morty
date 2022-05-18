package com.ruslangrigoriev.rickandmorty

import com.ruslangrigoriev.rickandmorty.data.local.CharactersDao
import com.ruslangrigoriev.rickandmorty.data.local.EpisodesDao
import com.ruslangrigoriev.rickandmorty.data.remote.CharactersService
import com.ruslangrigoriev.rickandmorty.data.repository.CharactersRepositoryImpl
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.StandardTestDispatcher
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
    private val coroutineScope: CoroutineScope =
        CoroutineScope(SupervisorJob() + StandardTestDispatcher())
    private lateinit var charactersService: CharactersService
    private lateinit var charactersDao: CharactersDao
    private lateinit var episodesDao: EpisodesDao

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

        episodesDao = mockk()
        coEvery { episodesDao.getListEpisodesByIds(any()) } returns testEpisodesList
        coEvery { episodesDao.insertEpisodes(any()) } returns Unit

        sut =
            CharactersRepositoryImpl(coroutineScope, charactersService, charactersDao, episodesDao)
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