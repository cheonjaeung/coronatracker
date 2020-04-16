package io.github.entimer.coronatracker.room.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import io.github.entimer.coronatracker.room.entity.CountryEntity

@Dao
interface CountryDao {
    @Query("SELECT * FROM country")
    fun selectAll(): List<CountryEntity>

    @Query("SELECT * FROM country WHERE name LIKE (:keyword) OR code LIKE (:keyword)")
    fun selectByKeyword(keyword: String): List<CountryEntity>

    @Query("SELECT name FROM country WHERE name = (:name)")
    fun selectByName(name: String): String

    @Query("SELECT name FROM country WHERE code = (:code)")
    fun selectByCode(code: String): String

    @Insert
    fun insert(country: CountryEntity)

    @Insert
    fun insertAll(vararg countries: CountryEntity)

    @Delete
    fun delete(country: CountryEntity)

    @Delete
    fun deleteAll(vararg country: CountryEntity)
}