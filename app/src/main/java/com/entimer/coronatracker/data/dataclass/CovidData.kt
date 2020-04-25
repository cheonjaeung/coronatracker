package com.entimer.coronatracker.data.dataclass

data class CovidData(
    val time: String,
    val country: String,
    val confirmed: Int,
    val actives: Int,
    val recovered: Int,
    val deaths: Int
)