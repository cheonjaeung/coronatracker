package io.github.entimer.coronatracker.api.country

import com.google.gson.annotations.SerializedName

data class Countries(
    @SerializedName(value = "countries")
    val countries: List<Country>)