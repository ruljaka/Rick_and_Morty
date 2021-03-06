package com.ruslangrigoriev.rickandmorty.core.data.coverters

import androidx.room.ProvidedTypeConverter
import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.ruslangrigoriev.rickandmorty.core.data.dto.character_dto.Origin

@ProvidedTypeConverter
class OriginConverter {
    private val gson = Gson()

    @TypeConverter
    fun toString(item: Origin): String {
        return gson.toJson(item)
    }

    @TypeConverter
    fun fromString(json: String): Origin {
        val listType = object : TypeToken<Origin>() {}.type
        return gson.fromJson(json, listType)
    }
}