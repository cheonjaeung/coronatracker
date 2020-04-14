package io.github.entimer.coronatracker.api.country

import com.google.gson.annotations.SerializedName

data class Country(
    @SerializedName(value = "Code")
    val code: String,

    @SerializedName(value = "Name")
    val name: String
)