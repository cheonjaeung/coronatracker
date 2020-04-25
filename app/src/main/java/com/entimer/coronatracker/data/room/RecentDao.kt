package com.entimer.coronatracker.data.room

import androidx.room.*

@Dao
interface RecentDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(data: RecentEntity)

    @Update
    fun update(data: RecentEntity)

    @Delete
    fun delete(data: RecentEntity)

    @Query("SELECT * FROM recent")
    fun select(): RecentEntity
}