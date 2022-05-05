package com.ruslangrigoriev.rickandmorty.data.local

import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.ruslangrigoriev.rickandmorty.data.dto.characterDTO.CharacterDTO
import com.ruslangrigoriev.rickandmorty.data.dto.episodeDTO.EpisodeDTO

@Dao
interface EpisodesDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertEpisodes(episodes: List<EpisodeDTO>)

    @Query("DELETE FROM EpisodeDTO")
    fun deleteAll(): Int

    @Query("SELECT * FROM EpisodeDTO WHERE id IN (:episodeIds)")
    suspend fun getListEpisodesByIds(episodeIds: List<Int>): List<EpisodeDTO>

}