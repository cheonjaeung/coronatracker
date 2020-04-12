package io.github.entimer.coronatracker.room.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import io.github.entimer.coronatracker.room.entity.Case
import io.github.entimer.coronatracker.room.entity.CaseNumber

@Dao
interface CaseDao {
    @Query("SELECT * FROM `case`")
    fun selectAll(): List<Case>

    @Query("SELECT confirmed, recovered, death FROM `case` WHERE id = (:id)")
    fun selectById(id: Int): List<CaseNumber>

    @Query("SELECT confirmed, recovered, death FROM `case` WHERE country = (:country)")
    fun selectByCountry(country: String): List<CaseNumber>

    @Query("SELECT confirmed, recovered, death FROM `case` WHERE country IN (:countries)")
    fun selectByCountries(vararg countries: String): List<CaseNumber>

    @Query("SELECT confirmed, recovered, death FROM `case` WHERE date = (:date)")
    fun selectByDate(date: String): List<CaseNumber>

    @Query("SELECT confirmed, recovered, death FROM `case` WHERE date IN (:dates)")
    fun selectByDates(vararg dates: String): List<CaseNumber>

    @Insert
    fun insert(case: Case)

    @Insert
    fun insertAll(vararg cases: Case)

    @Delete
    fun delete(case: Case)

    @Delete
    fun deleteAll(vararg cases: Case)
}