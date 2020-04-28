package com.entimer.coronatracker.util

import com.entimer.coronatracker.data.dataclass.CountryData
import com.entimer.coronatracker.util.api.ApiCountryData
import com.entimer.coronatracker.util.api.ApiRecentData
import com.entimer.coronatracker.data.dataclass.CovidData
import com.entimer.coronatracker.util.api.ApiCountryListDataCountry

fun apiRecentData2CovidData(data: ApiRecentData): CovidData {
    val time = data.lastUpdate
    val confirmed = data.confirmed.value
    val recovered = data.recovered.value
    val deaths = data.deaths.value
    val actives = confirmed - recovered - deaths
    return CovidData(time, "Global", confirmed, actives, recovered, deaths)
}

fun apiCountryData2CovidData(country: String, data: ApiCountryData): CovidData {
    val time = data.lastUpdate
    val confirmed = data.confirmed.value
    val recovered = data.recovered.value
    val deaths = data.deaths.value
    val actives = confirmed - recovered - deaths
    return CovidData(time, country, confirmed, actives, recovered, deaths)
}

fun apiCountryListDataCountry2CountryData(data: ApiCountryListDataCountry): CountryData {
    val name = data.name
    val iso3 = data.iso3
    return CountryData(name, iso3)
}