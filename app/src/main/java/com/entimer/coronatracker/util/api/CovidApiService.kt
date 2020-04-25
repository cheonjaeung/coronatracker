package com.entimer.coronatracker.util.api

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class CovidApiService {
    companion object {
        fun getService(): CovidApi {
            val retrofit = Retrofit.Builder()
                .baseUrl("https://covid19.mathdro.id/")
                .addConverterFactory(GsonConverterFactory.create())
                .build()
            return retrofit.create(CovidApi::class.java)
        }
    }
}