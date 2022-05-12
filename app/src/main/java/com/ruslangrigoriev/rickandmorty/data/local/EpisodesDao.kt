package com.ruslangrigoriev.rickandmorty.data.local

import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.ruslangrigoriev.rickandmorty.data.dto_and_entity.episodeDTO.EpisodeDTO

@Dao
interface EpisodesDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertEpisodes(episodes: List<EpisodeDTO>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertEpisode(episode: EpisodeDTO)

    @Query(
        """SELECT * FROM EpisodeDTO
        WHERE (:name IS NULL OR name LIKE '%' || :name || '%' )
        AND (:episode IS NULL OR episode LIKE  '%' || :episode || '%')"""
    )
    fun getEpisodes(
        name: String? = null,
        episode: String? = null
    ): PagingSource<Int, EpisodeDTO>

    @Query("SELECT * FROM EpisodeDTO WHERE id = :episodeID")
    suspend fun getEpisodeById(episodeID: Int): EpisodeDTO?

    @Query("SELECT * FROM EpisodeDTO WHERE id IN (:episodeIds)")
    suspend fun getListEpisodesByIds(episodeIds: List<Int>): List<EpisodeDTO>?

    @Query("DELETE FROM EpisodeDTO")
    fun deleteAll(): Int

}