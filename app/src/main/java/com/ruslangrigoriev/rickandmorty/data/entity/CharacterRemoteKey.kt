package com.ruslangrigoriev.rickandmorty.data.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class CharacterRemoteKey(
    @PrimaryKey
    val id: Int,
    val prevKey: Int?,
    val nextKey: Int?
)
