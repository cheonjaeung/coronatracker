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

    class BackgroundTask(context: Context, presenter: MainPresenter): AsyncTask<String, String, String>() {
        private val context = context
        private val presenter = presenter
        private lateinit var body: Countries

        override fun doInBackground(vararg params: String?): String {
            val service = DatasetAPIService.getService()

            service.getData().enqueue( object: Callback<Countries> {
                override fun onResponse(call: Call<Countries>, response: Response<Countries>) {
                    if(response.isSuccessful) {
                        body = response.body()!!
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

            val db = AppDatabase.getDatabase(context)

            for(country in body.countries) {
                if(db.countryDao().selectByName(country.name) != country.name) {
                    val entry = CountryEntity(country.name, country.code)
                    db.countryDao().insert(entry)
                }
            }

            db.close()
            return "Success"
        }
    }

    fun getData(context: Context) {
        val task = BackgroundTask(context, presenter)
        task.execute()
    }
}