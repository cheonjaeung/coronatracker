package com.entimer.coronatracker.data.room.dao

import androidx.room.*
import com.entimer.coronatracker.data.room.entity.RecentEntity

@Dao
interface RecentDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(data: RecentEntity)

    @Update
    fun update(data: RecentEntity)

    @Delete
    fun delete(data: RecentEntity)

    @Query("SELECT * FROM recent WHERE id = 0")
    fun select(): RecentEntity
}