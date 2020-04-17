package io.github.entimer.coronatracker.util.dataclass

data class CaseData(
    val date: String,
    val confirmed: Int,
    val recovered: Int,
    val death: Int
)