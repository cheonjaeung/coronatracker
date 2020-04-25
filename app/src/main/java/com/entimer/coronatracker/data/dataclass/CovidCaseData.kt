package com.entimer.coronatracker.data.dataclass

data class CovidCaseData(
    val date: String,
    val country: String,
    val confirmed: Int,
    val actives: Int,
    val recovered: Int,
    val deaths: Int
)