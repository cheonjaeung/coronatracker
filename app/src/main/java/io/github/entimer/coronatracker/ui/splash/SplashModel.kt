package io.github.entimer.coronatracker.ui.splash

import android.content.Context
import android.util.Log
import com.google.gson.JsonParser
import io.github.entimer.coronatracker.api.iso3166.Iso3166ApiService
import io.github.entimer.coronatracker.api.iso3166.Iso3166Data
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class SplashModel(presenter: SplashPresenter) {
    private val presenter = presenter
    private val logTag: String = "SplashModel"
    val parser = JsonParser()

    fun getIso3166Codes(context: Context) {
        Iso3166ApiService.getService().getIso3166List().enqueue(object: Callback<ResponseBody> {
            override fun onResponse(call: Call<ResponseBody>, response: Response<ResponseBody>) {
                if(response.isSuccessful) {
                    val body = response.body()!!.string()
                    val fullData = parser.parse(body).asJsonObject
                    val listData = fullData["list"].asJsonArray

                    val iso3166List = ArrayList<Iso3166Data>()

                    for(data in listData) {
                        val jsonData = data.asJsonObject
                        val iso3166 = Iso3166Data(
                            jsonData["name"].asString,
                            jsonData["alpha-2"].asString,
                            jsonData["alpha-3"].asString,
                            jsonData["numeric"].asString
                        )
                        iso3166List.add(iso3166)
                    }

                    presenter.onSuccess()
                }
                else {
                    Log.e(logTag, "API response is not successful: ${response.errorBody()}")
                    presenter.onFailure()
                }
            }

            override fun onFailure(call: Call<ResponseBody>, t: Throwable) {
                Log.e(logTag, "API failure: ${t.stackTrace}")
                presenter.onFailure()
            }
        })
    }
}