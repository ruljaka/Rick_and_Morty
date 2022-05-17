package com.ruslangrigoriev.rickandmorty.data.local

import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.ruslangrigoriev.rickandmorty.data.dto_and_entity.characters.Character

@Dao
interface CharactersDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertCharacters(characters: List<Character>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertCharacter(character: Character)

    @Query(
        """SELECT * FROM Character 
        WHERE (:name IS NULL OR name LIKE '%' || :name || '%' ) 
        AND (:status IS NULL OR status = :status) 
        AND (:species IS NULL OR species LIKE '%' || :species || '%' ) 
        AND (:type IS NULL OR type LIKE '%' || :type || '%')
        AND (:gender IS NULL OR gender = :gender)"""
    )
    fun getCharacters(
        name: String? = null,
        status: String? = null,
        species: String? = null,
        type: String? = null,
        gender: String? = null
    ): PagingSource<Int, Character>

    @Query("SELECT * FROM Character WHERE id = :characterID")
    suspend fun getCharacterById(characterID: Int): Character?

    @Query("SELECT * FROM Character WHERE id IN (:characterIds)")
    suspend fun getListCharactersByIds(characterIds: List<Int>): List<Character>?

    @Query("DELETE FROM Character")
    suspend fun deleteAll(): Int

}