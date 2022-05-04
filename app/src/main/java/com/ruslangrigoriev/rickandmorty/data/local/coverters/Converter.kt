package com.ruslangrigoriev.rickandmorty.data.local.coverters


interface Converter<I> {
    fun toString(item: I): String
    fun fromString(json: String): I
}