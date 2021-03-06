package com.ruslangrigoriev.rickandmorty.core.data.coverters

import androidx.room.ProvidedTypeConverter
import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.ruslangrigoriev.rickandmorty.core.data.dto.character_dto.Location

@ProvidedTypeConverter
class LocationConverter {
    private val gson = Gson()

    @TypeConverter
    fun toString(item: Location): String {
        return gson.toJson(item)
    }

    @TypeConverter
    fun fromString(json: String): Location {
        val listType = object : TypeToken<Location>() {}.type
        return gson.fromJson(json, listType)
    }
}