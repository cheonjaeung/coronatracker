package com.entimer.coronatracker.room

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.entimer.coronatracker.room.dao.Iso3166Dao
import com.entimer.coronatracker.room.entity.Iso3166Entity

@Database(entities = [Iso3166Entity::class], version = 1)
abstract class AppDatabase: RoomDatabase() {
    abstract fun iso3166Dao(): Iso3166Dao

    companion object {
        private lateinit var INSTANCE: AppDatabase
        private var IS_USED = false

        fun getDatabase(context: Context): AppDatabase {
            if(!IS_USED) {
                IS_USED = true

                INSTANCE = Room.databaseBuilder(context, AppDatabase::class.java, "coronatracker.db")
                    .build()
            }
            return INSTANCE
        }
    }
}