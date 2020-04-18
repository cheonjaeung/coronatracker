package io.github.entimer.coronatracker.api.iso3166

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class Iso3166ApiService {
    companion object {
        fun getService(): Iso3166Api {
            val retrofit = Retrofit.Builder()
                .baseUrl("https://entimer.github.io/Iso3166/")
                .addConverterFactory(GsonConverterFactory.create())
                .build()
            return retrofit.create(Iso3166Api::class.java)
        }
    }
}