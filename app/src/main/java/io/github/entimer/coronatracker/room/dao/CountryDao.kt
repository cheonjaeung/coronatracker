package io.github.entimer.coronatracker.room.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import io.github.entimer.coronatracker.room.entity.Country

@Dao
interface CountryDao {
    @Query("SELECT * FROM country")
    fun selectAll(): List<Country>

    @Query("SELECT name FROM country WHERE name = (:name)")
    fun selectByName(name: String): String

    @Query("SELECT name FROM country WHERE name IN (:names)")
    fun selectByNames(vararg names: String): List<String>

    @Query("SELECT name FROM country WHERE code = (:code)")
    fun selectByCode(code: String): String

    @Query("SELECT name FROM country WHERE code IN (:codes)")
    fun selectByCodes(vararg codes: String): List<String>

    @Query("SELECT code FROM country WHERE name = (:name)")
    fun selectCodeByName(name: String): String

    @Query("SELECT code FROM country WHERE name IN (:names)")
    fun selectCodeByNames(vararg names: String): List<String>

    @Query("SELECT code FROM country WHERE code = (:code)")
    fun selectCodeByCode(code: String): String

    @Query("SELECT code FROM country WHERE code IN (:codes)")
    fun selectCodeByCodes(vararg codes: String): List<String>

    @Insert
    fun insert(country: Country)

    @Insert
    fun insertAll(vararg countries: Country)

    @Delete
    fun delete(country: Country)

    @Delete
    fun deleteAll(vararg country: Country)
}