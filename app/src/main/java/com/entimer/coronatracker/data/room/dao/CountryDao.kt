package com.entimer.coronatracker.data.room.dao

import androidx.room.*
import com.entimer.coronatracker.data.room.entity.CountryEntity

@Dao
interface CountryDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(data: CountryEntity)

    @Update
    fun update(data: CountryEntity)

    @Delete
    fun delete(data: CountryEntity)

    @Query("SELECT * FROM country WHERE name LIKE (:keyword)")
    fun select(keyword: String): List<CountryEntity>
}