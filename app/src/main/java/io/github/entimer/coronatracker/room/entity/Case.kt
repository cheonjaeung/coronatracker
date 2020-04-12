package io.github.entimer.coronatracker.room.entity

import androidx.room.*
import androidx.room.ForeignKey.CASCADE

@Entity(tableName = "case",
    foreignKeys = [
        ForeignKey(
            entity = Country::class,
            parentColumns = ["country_name"],
            childColumns = ["country"],
            onDelete = CASCADE
        )
    ])
data class Case (
    @PrimaryKey
    val id: Int,

    val country: String,

    val date: String,

    @Embedded
    val number: CaseNumber
)