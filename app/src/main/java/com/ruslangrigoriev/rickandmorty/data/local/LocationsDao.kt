package com.ruslangrigoriev.rickandmorty.data.local

import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.ruslangrigoriev.rickandmorty.data.dto_and_entity.locationDTO.LocationDTO

@Dao
interface LocationsDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertLocations(locations: List<LocationDTO>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertLocation(location: LocationDTO)

    @Query(
        """SELECT * FROM LocationDTO
        WHERE (:name IS NULL OR name LIKE '%' || :name || '%' )
        AND (:type IS NULL OR type LIKE  '%' || :type || '%')
        AND (:dimension IS NULL OR dimension LIKE  '%' || :dimension || '%')"""
    )
    fun getLocations(
        name: String? = null,
        type: String? = null,
        dimension: String? = null
    ): PagingSource<Int, LocationDTO>

    @Query("SELECT * FROM LocationDTO WHERE id = :locationID")
    suspend fun getLocationById(locationID: Int): LocationDTO?

    @Query("DELETE FROM LocationDTO")
    fun deleteAll(): Int

}