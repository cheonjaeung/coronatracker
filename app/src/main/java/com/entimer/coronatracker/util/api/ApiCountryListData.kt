package com.entimer.coronatracker.util.api

data class ApiCountryListData(
    val countries: ArrayList<ApiCountryListDataCountry>
)

data class ApiCountryListDataCountry (
    val name: String,
    val iso2: String?,
    val iso3: String?
)