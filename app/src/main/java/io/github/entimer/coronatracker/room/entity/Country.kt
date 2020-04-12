package io.github.entimer.coronatracker.room.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "country")
data class Country (
    @PrimaryKey
    val name: String,

    val code: String
)