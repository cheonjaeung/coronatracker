package com.entimer.coronatracker.data.dataclass

data class ApiCountryData(
    val countries: List<ApiCountryDataCountry>
)

data class ApiCountryDataCountry(
    val name: String,
    val iso2: String?,
    val iso3: String?
)