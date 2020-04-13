package io.github.entimer.coronatracker.room.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import io.github.entimer.coronatracker.room.entity.Case

@Dao
interface CaseDao {
    @Query("SELECT * FROM cases")
    fun selectAll(): List<Case>

    @Query("SELECT * FROM cases WHERE country = (:country)")
    fun selectByCountry(country: String): List<Case>

    @Query("SELECT * FROM cases WHERE country IN (:countries)")
    fun selectByCountries(vararg countries: String): List<Case>

    @Query("SELECT * FROM cases WHERE date = (:date)")
    fun selectByDate(date: String): List<Case>

    @Query("SELECT * FROM cases WHERE date IN (:dates)")
    fun selectByDates(vararg dates: String): List<Case>

    @Query("SELECT confirmed FROM cases WHERE date = (:date)")
    fun selectAllConfirmedByDate(date: String): List<Int>

    @Query("SELECT recovered FROM cases WHERE date = (:date)")
    fun selectAllRecoveredByDate(date: String): List<Int>

    @Query("SELECT death FROM cases WHERE date = (:date)")
    fun selectAllDeathByDate(date: String): List<Int>

    @Insert
    fun insert(case: Case)

    @Insert
    fun insertAll(vararg cases: Case)

    @Delete
    fun delete(case: Case)

    @Delete
    fun deleteAll(vararg cases: Case)
}