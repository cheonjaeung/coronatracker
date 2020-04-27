package com.entimer.coronatracker.util.api

data class ApiCountryData(
    val confirmed: ApiCountryDataValue,
    val recovered: ApiCountryDataValue,
    val deaths: ApiCountryDataValue,
    val lastUpdate: String
)

data class ApiCountryDataValue(
    val value: Int,
    val detail: String
)