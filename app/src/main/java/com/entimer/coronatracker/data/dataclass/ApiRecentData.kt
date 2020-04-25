package com.entimer.coronatracker.data.dataclass

data class ApiRecentData(
    val confirmed: ApiRecentDataCount,
    val recovered: ApiRecentDataCount,
    val deaths: ApiRecentDataCount,
    val dailySummary: String,
    val dailyTimeSeries: ApiRecentDataStrings,
    val image: String,
    val source: String,
    val countries: String,
    val countryDetail: ApiRecentDataStrings,
    val lastUpdate: String
)

data class ApiRecentDataCount (
    val value: Int,
    val detail: String
)

data class ApiRecentDataStrings (
    val pattern: String,
    val example: String
)