package io.github.entimer.coronatracker.api

import io.github.entimer.coronatracker.api.country.CountryAPI
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class DatasetAPIService {
    companion object {
        fun getService(): CountryAPI {
            val retrofit = Retrofit.Builder()
                .baseUrl("https://entimer.github.io/Datasets/")
                .addConverterFactory(GsonConverterFactory.create())
                .build()

            return retrofit.create(CountryAPI::class.java)
        }
    }
}