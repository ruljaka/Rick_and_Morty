package com.ruslangrigoriev.rickandmorty.data.local

import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.ruslangrigoriev.rickandmorty.data.dto_and_entity.episodes.Episode

@Dao
interface EpisodesDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertEpisodes(episodes: List<Episode>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertEpisode(episode: Episode)

    @Query(
        """SELECT * FROM Episode
        WHERE (:name IS NULL OR name LIKE '%' || :name || '%' )
        AND (:episode IS NULL OR episode LIKE  '%' || :episode || '%')"""
    )
    fun getEpisodes(
        name: String? = null,
        episode: String? = null
    ): PagingSource<Int, Episode>

    @Query("SELECT * FROM Episode WHERE id = :episodeID")
    suspend fun getEpisodeById(episodeID: Int): Episode?

    @Query("SELECT * FROM Episode WHERE id IN (:episodeIds)")
    suspend fun getListEpisodesByIds(episodeIds: List<Int>): List<Episode>?

    @Query("DELETE FROM Episode")
    fun deleteAll(): Int

}