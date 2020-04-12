package io.github.entimer.coronatracker.room

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import io.github.entimer.coronatracker.room.dao.CaseDao
import io.github.entimer.coronatracker.room.dao.CountryDao
import io.github.entimer.coronatracker.room.entity.Country

@Database(entities = [Country::class], version = 1)
abstract class AppDatabase: RoomDatabase() {
    abstract fun countryDao(): CountryDao
    abstract fun caseDao(): CaseDao

    companion object {
        private lateinit var INSTANCE: AppDatabase
        private var IS_USED = false

        fun getDatabase(context: Context): AppDatabase {
            if(!IS_USED) {
                IS_USED = true

                INSTANCE = Room.databaseBuilder(context, AppDatabase::class.java, "corona_tracker_database").build()
            }
            return INSTANCE
        }
    }
}