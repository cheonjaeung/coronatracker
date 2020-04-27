package com.entimer.coronatracker.data.dataclass

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