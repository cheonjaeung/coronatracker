package io.github.entimer.coronatracker.api.covid

import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path

interface CovidApi {
    @GET("latest-date")
    fun getLatestDate(): Call<ResponseBody>

    @GET("global/{date}")
    fun getGlobal(@Path("date")date: String): Call<ResponseBody>

    @GET("global/count")
    fun getGlobalEveryDates(): Call<ResponseBody>

    @GET("global/latest")
    fun getGlobalEveryCountries(): Call<ResponseBody>
}