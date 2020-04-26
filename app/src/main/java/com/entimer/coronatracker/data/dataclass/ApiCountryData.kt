package com.entimer.coronatracker.data.dataclass

data class ApiCountryData(
    val countries: ArrayList<ApiCountryDataCountry>
)

data class ApiCountryDataCountry(
    val name: String,
    val iso2: String?,
    val iso3: String?
)