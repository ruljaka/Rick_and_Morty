package com.ruslangrigoriev.rickandmorty.presentation.mappers

interface Mapper<I, L, O> {
    fun map(input: I, list : L): O
}