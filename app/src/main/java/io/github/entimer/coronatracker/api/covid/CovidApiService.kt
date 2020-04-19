package io.github.entimer.coronatracker.api.covid

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class CovidApiService {
    companion object {
        fun getService(): CovidApi {
            val retrofit = Retrofit.Builder()
                .baseUrl("https://covidapi.info/api/v1/")
                .addConverterFactory(GsonConverterFactory.create())
                .build()
            return retrofit.create(CovidApi::class.java)
        }
    }
}