package com.ruslangrigoriev.rickandmorty.domain.mappers

interface Mapper<I, L, O> {
    fun map(input: I, list : L): O
}