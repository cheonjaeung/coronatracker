package io.github.entimer.coronatracker.api.iso3166

import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.http.GET

interface Iso3166Api {
    @GET("list.json")
    fun getIso3166List(): Call<ResponseBody>
}