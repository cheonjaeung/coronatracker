package io.github.entimer.coronatracker.room.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "country")
data class CountryEntity (
    @PrimaryKey
    val name: String,

    val code: String?
)