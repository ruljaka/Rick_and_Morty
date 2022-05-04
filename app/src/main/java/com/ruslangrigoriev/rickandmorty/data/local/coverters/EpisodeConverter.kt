package com.ruslangrigoriev.rickandmorty.data.local.coverters

import androidx.room.ProvidedTypeConverter
import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

@ProvidedTypeConverter
object EpisodeConverter : Converter<List<String>> {
    private val gson = Gson()

    @TypeConverter
    override fun toString(item: List<String>): String {
        return gson.toJson(item)
    }

    @TypeConverter
    override fun fromString(json: String): List<String> {
        val listType = object : TypeToken<List<String>>() {}.type
        return gson.fromJson(json, listType)
    }
}