package com.entimer.coronatracker.data.room

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.entimer.coronatracker.data.room.dao.CountryDao
import com.entimer.coronatracker.data.room.dao.RecentDao
import com.entimer.coronatracker.data.room.entity.CountryEntity
import com.entimer.coronatracker.data.room.entity.RecentEntity

@Database(entities = [RecentEntity::class, CountryEntity::class], version = 1)
abstract class CoronaTrackerRoom: RoomDatabase() {
    abstract fun recentDao(): RecentDao
    abstract fun countryDao(): CountryDao

    companion object {
        private lateinit var INSTANCE: CoronaTrackerRoom
        private var IS_USED = false

        fun getDatabase(context: Context): CoronaTrackerRoom {
            if(!IS_USED) {
                IS_USED = true

                INSTANCE = Room.databaseBuilder(context, CoronaTrackerRoom::class.java, "coronatracker.db")
                    .build()
            }
            return INSTANCE
        }
    }
}