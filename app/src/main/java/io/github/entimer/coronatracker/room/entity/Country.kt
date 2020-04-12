package io.github.entimer.coronatracker.room.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "country")
data class Country (
    @PrimaryKey
    @ColumnInfo(name = "country_name")
    val countryName: String
)