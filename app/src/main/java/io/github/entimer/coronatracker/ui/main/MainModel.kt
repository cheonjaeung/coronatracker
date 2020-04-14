package io.github.entimer.coronatracker.ui.main

import android.content.Context
import android.util.Log
import com.google.gson.Gson
import io.github.entimer.coronatracker.api.DatasetAPIService
import io.github.entimer.coronatracker.api.country.Countries
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainModel(presenter: MainPresenter) {
    private val presenter = presenter

    fun getCountriesFromWeb(context: Context) {
        val service = DatasetAPIService.getService()

        service.getData().enqueue( object: Callback<Countries> {
                override fun onResponse(call: Call<Countries>, response: Response<Countries>) {
                    if(response.isSuccessful) {
                        val gson = Gson()
                        val body = response.body()
                        Log.d("MainModel", "Countries API onResponse is successful: $body")
                        for(temp in body!!.countries) {
                            Log.d("MainModel", "Country(Code: ${temp.code}, Name: ${temp.name})")
                        }
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
}