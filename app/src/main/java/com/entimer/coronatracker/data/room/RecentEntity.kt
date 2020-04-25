package com.entimer.coronatracker.data.room

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "recent")
data class RecentEntity(
    @PrimaryKey
    val id: Int,
    val time: String,
    val country: String,
    val confirmed: Int,
    val actives: Int,
    val recovered: Int,
    val deaths: Int
)