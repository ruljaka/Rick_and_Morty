package com.ruslangrigoriev.rickandmorty.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.ruslangrigoriev.rickandmorty.data.entity.CharacterRemoteKey

@Dao
interface CharacterRemoteKeyDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(remoteKey: List<CharacterRemoteKey>)

    @Query("SELECT * FROM CharacterRemoteKey WHERE id = :id")
    suspend fun getRemoteKeyById(id: Int): CharacterRemoteKey?

    @Query("DELETE FROM CharacterRemoteKey")
    suspend fun deleteAll()
}