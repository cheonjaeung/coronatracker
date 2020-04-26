package com.entimer.coronatracker.data.room.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "country")
data class CountryEntity(
    @PrimaryKey
    val iso3: String,
    val name: String
)