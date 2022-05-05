package com.ruslangrigoriev.rickandmorty.data.local.coverters

import androidx.room.ProvidedTypeConverter
import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.ruslangrigoriev.rickandmorty.data.dto.characterDTO.Origin

@ProvidedTypeConverter
object OriginConverter : Converter<Origin> {
    private val gson = Gson()

    @TypeConverter
    override fun toString(item: Origin): String {
        return gson.toJson(item)
    }

    @TypeConverter
    override fun fromString(json: String): Origin {
        val listType = object : TypeToken<Origin>() {}.type
        return gson.fromJson(json, listType)
    }
}