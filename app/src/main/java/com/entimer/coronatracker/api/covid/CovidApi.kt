package com.entimer.coronatracker.api.covid

import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path

interface CovidApi {
    @GET("global/count")
    fun getGlobal(): Call<ResponseBody>

    @GET("country/{code}")
    fun getCountry(@Path("code")countryCode: String): Call<ResponseBody>
}