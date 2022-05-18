package com.ruslangrigoriev.rickandmorty

import com.ruslangrigoriev.rickandmorty.data.dto_and_entity.episodes.Episode
import com.ruslangrigoriev.rickandmorty.domain.repository.EpisodesRepository
import com.ruslangrigoriev.rickandmorty.domain.useCases.episodes.GetEpisodeByIdUseCase
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.runBlocking
import org.hamcrest.CoreMatchers.equalTo
import org.hamcrest.MatcherAssert.assertThat

import org.junit.Test

class GetEpisodeByIdUseCaseTest {

    @Test
    fun `should return episode`() {
        val testEpisode: Episode = mockk(relaxed = true)
        val testID = 1

        val episodesRepository: EpisodesRepository = mockk()
        coEvery { episodesRepository.getEpisodeById(any()) } returns testEpisode

        val sut = GetEpisodeByIdUseCase(episodesRepository)

        runBlocking {
            val actualEpisode = sut.invoke(testID)
            assertThat(actualEpisode, equalTo(testEpisode))
        }
        coVerify { episodesRepository.getEpisodeById(any()) }
    }
}