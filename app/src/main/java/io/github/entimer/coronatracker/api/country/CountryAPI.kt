package io.github.entimer.coronatracker.api.country

import retrofit2.Call
import retrofit2.http.GET

interface CountryAPI {
    @GET("countries.json")
    fun getData(): Call<Countries>
}