package com.entimer.coronatracker.util.api

import com.entimer.coronatracker.data.dataclass.ApiCountryData
import com.entimer.coronatracker.data.dataclass.ApiRecentData
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path

interface CovidApi {
    @GET("api")
    fun getRecentData(): Call<ApiRecentData>

    @GET("api/countries/{country}")
    fun getCountriesData(@Path("country")country: String): Call<ApiCountryData>
}