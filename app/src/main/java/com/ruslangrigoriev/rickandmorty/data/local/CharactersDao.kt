package com.ruslangrigoriev.rickandmorty.data.local

import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.ruslangrigoriev.rickandmorty.data.dto.characterDTO.CharacterDTO
import com.ruslangrigoriev.rickandmorty.data.dto.episodeDTO.EpisodeDTO

@Dao
interface CharactersDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertCharacters(characters: List<CharacterDTO>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertCharacter(character: CharacterDTO)

    @Query(
        """SELECT * FROM CharacterDTO 
        WHERE (:name IS NULL OR name LIKE :name || '%' ) 
        AND (:status IS NULL OR status = :status) 
        AND (:species IS NULL OR species LIKE :species || '%' ) 
        AND (:type IS NULL OR type LIKE :type || '%')
        AND (:gender IS NULL OR gender = :gender)"""
    )
    fun getCharacters(
        name: String? = null,
        status: String? = null,
        species: String? = null,
        type: String? = null,
        gender: String? = null
    ): PagingSource<Int, CharacterDTO>

    @Query("SELECT * FROM CharacterDTO WHERE id = :characterID")
    suspend fun getCharacterById(characterID: Int): CharacterDTO

    @Query("SELECT * FROM CharacterDTO WHERE id IN (:characterIds)")
    suspend fun getListCharactersByIds(characterIds: List<Int>): List<CharacterDTO>

    @Query("DELETE FROM CharacterDTO")
    fun deleteAll(): Int

}