package io.github.entimer.coronatracker.room.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "iso3166")
data class Iso3166Entity(
    val name: String,
    val alpha2: String,
    @PrimaryKey
    val alpha3: String,
    val numeric: String
)