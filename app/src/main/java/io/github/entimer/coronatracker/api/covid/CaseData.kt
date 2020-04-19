package io.github.entimer.coronatracker.api.covid

data class CaseData(
    val date: String,
    val confirmed: Int,
    val recovered: Int,
    val death: Int
)