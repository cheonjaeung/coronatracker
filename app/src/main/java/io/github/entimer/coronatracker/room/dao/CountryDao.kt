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

    @Query("SELECT country_name FROM country WHERE country_name = (:country)")
    fun selectByCountry(country: String): String

    @Query("SELECT country_name FROM country WHERE country_name IN (:countries)")
    fun selectByCountries(countries: List<String>): List<String>

    @Insert
    fun insert(country: Country)

    @Insert
    fun insertAll(vararg countries: Country)

    @Delete
    fun delete(country: Country)

    @Delete
    fun deleteAll(vararg country: Country)
}