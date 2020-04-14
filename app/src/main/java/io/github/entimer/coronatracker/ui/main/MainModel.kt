package io.github.entimer.coronatracker.ui.main

import android.content.Context
import android.os.AsyncTask
import android.util.Log
import io.github.entimer.coronatracker.api.DatasetAPIService
import io.github.entimer.coronatracker.api.country.Countries
import io.github.entimer.coronatracker.room.AppDatabase
import io.github.entimer.coronatracker.room.entity.CountryEntity
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainModel(presenter: MainPresenter) {
    private val presenter = presenter

    fun initCountriesDatabase(context: Context) {
        getCountriesFromAPI(context)
    }

    private fun getCountriesFromAPI(context: Context) {
        val service = DatasetAPIService.getService()

        service.getData().enqueue( object: Callback<Countries> {
                override fun onResponse(call: Call<Countries>, response: Response<Countries>) {
                    if(response.isSuccessful) {
                        val body = response.body()!!
                        setCountriesInDatabase(context, body)
                    }
                    else {
                        Log.d("MainModel", "Countries API onResponse is failure: $response")
                    }
                }

                override fun onFailure(call: Call<Countries>, t: Throwable) {
                    Log.d("MainModel", "Countries API onFailure: $t")
                }
            }
        )
    }

    private fun setCountriesInDatabase(context: Context, countries: Countries) {
        Thread (
            Runnable {
                val db = AppDatabase.getDatabase(context)

                for(country in countries.countries) {
                    if(db.countryDao().selectByName(country.name) != country.name) {
                        val entry = CountryEntity(country.name, country.code)
                        db.countryDao().insert(entry)
                    }
                }

                db.close()
            }
        )
    }
}