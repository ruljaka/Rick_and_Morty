package com.ruslangrigoriev.rickandmorty.episodes.data.local

import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.ruslangrigoriev.rickandmorty.core.data.dto.character_dto.Character
import com.ruslangrigoriev.rickandmorty.core.data.dto.episode_dto.Episode

@Dao
interface EpisodesDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertEpisodes(episodes: List<Episode>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertEpisode(episode: Episode)

    @Query("SELECT * FROM Episode WHERE id = :episodeID")
    suspend fun getEpisodeById(episodeID: Int): Episode?

    @Query("DELETE FROM Episode")
    fun deleteAll(): Int

    @Query(
        """SELECT * FROM Episode
        WHERE (:name IS NULL OR name LIKE '%' || :name || '%' )
        AND (:episode IS NULL OR episode LIKE  '%' || :episode || '%')"""
    )
    fun getEpisodes(
        name: String? = null,
        episode: String? = null
    ): PagingSource<Int, Episode>

    @Query("SELECT * FROM Character WHERE id IN (:characterIds)")
    suspend fun getListCharactersByIds(characterIds: List<Int>): List<Character>?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertCharacters(characters: List<Character>)

}