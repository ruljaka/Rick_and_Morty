package com.ruslangrigoriev.rickandmorty

import com.ruslangrigoriev.rickandmorty.core.data.dto.character_dto.Origin
import com.ruslangrigoriev.rickandmorty.core.data.coverters.OriginConverter
import org.hamcrest.CoreMatchers.equalTo
import org.hamcrest.MatcherAssert.assertThat
import org.junit.Before
import org.junit.Test

class OriginConverterTest {
    private val testOrigin =
        Origin(name = "Earth (C-137)", url = "https://rickandmortyapi.com/api/location/1")
    private val testJson =
        "{\"name\":\"Earth (C-137)\",\"url\":\"https://rickandmortyapi.com/api/location/1\"}"
    private lateinit var sut: OriginConverter

    @Before
    fun setUp() {
        sut = OriginConverter()
    }

    @Test
    fun `should convert Origin to Json`() {
        val actual = sut.toString(testOrigin)
        assertThat(actual, equalTo(testJson))
    }

    @Test
    fun `should convert Json to Origin`() {
        val actual = sut.fromString(testJson)
        assertThat(actual, equalTo(testOrigin))
    }
}