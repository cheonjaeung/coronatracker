package io.github.entimer.coronatracker.api.covid

import retrofit2.Retrofit

class CovidApiService {
    companion object {
        fun getService(): CovidApi {
            val retrofit = Retrofit.Builder().baseUrl("https://covidapi.info/api/v1/").build()
            return retrofit.create(CovidApi::class.java)
        }
    }
}