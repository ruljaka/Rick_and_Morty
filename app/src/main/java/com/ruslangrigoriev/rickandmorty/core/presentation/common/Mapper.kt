package com.ruslangrigoriev.rickandmorty.core.presentation.common


interface Mapper<I, L, O> {
    fun map(input: I, list: L): O
}