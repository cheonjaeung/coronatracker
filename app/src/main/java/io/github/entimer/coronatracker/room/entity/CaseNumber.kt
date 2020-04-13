package io.github.entimer.coronatracker.room.entity

data class CaseNumber(
    val confirmed: Int,
    val recovered: Int,
    val death: Int
)