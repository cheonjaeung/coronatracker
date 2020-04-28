package com.entimer.coronatracker.util.api

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path

interface CovidApi {
    @GET("api")
    fun getRecentData(): Call<ApiRecentData>

    @GET("api/countries/{country}")
    fun getCountriesData(@Path("country")country: String): Call<ApiCountryData>

    @GET("api/countries")
    fun getCountriesList(): Call<ApiCountryListData>
}