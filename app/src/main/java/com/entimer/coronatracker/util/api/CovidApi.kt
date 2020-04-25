package com.entimer.coronatracker.util.api

import com.entimer.coronatracker.data.dataclass.ApiRecentData
import retrofit2.Call
import retrofit2.http.GET

interface CovidApi {
    @GET("api")
    fun getRecentData(): Call<ApiRecentData>
}