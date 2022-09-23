package com.ruslangrigoriev.rickandmorty.data.source.local

import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.ruslangrigoriev.rickandmorty.domain.entity.locations.Location

@Dao
interface LocationsDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertLocations(locations: List<Location>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertLocation(location: Location)

    @Query(
        """SELECT * FROM Location
        WHERE (:name IS NULL OR name LIKE '%' || :name || '%' )
        AND (:type IS NULL OR type LIKE  '%' || :type || '%')
        AND (:dimension IS NULL OR dimension LIKE  '%' || :dimension || '%')"""
    )
    fun getLocations(
        name: String? = null,
        type: String? = null,
        dimension: String? = null
    ): PagingSource<Int, Location>

    @Query("SELECT * FROM Location WHERE id = :locationID")
    suspend fun getLocationById(locationID: Int): Location?

    @Query("DELETE FROM Location")
    fun deleteAll(): Int

}