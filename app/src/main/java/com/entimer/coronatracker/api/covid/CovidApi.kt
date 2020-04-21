package com.entimer.coronatracker.api.covid

import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.http.GET

interface CovidApi {
    @GET("global/count")
    fun getGlobal(): Call<ResponseBody>
}