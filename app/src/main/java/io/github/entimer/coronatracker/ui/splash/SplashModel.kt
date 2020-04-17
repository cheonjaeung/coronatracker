package io.github.entimer.coronatracker.ui.splash

import android.content.Context
import android.util.Log
import io.github.entimer.coronatracker.api.covid.CovidApiService
import io.github.entimer.coronatracker.util.SharedPreferenceUtil
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class SplashModel(presenter: SplashPresenter) {
    private val presenter = presenter

    fun getLatestDate(context: Context) {
        CovidApiService.getService().getLatestDate().enqueue(object: Callback<ResponseBody> {
            override fun onResponse(call: Call<ResponseBody>, response: Response<ResponseBody>) {
                if(response.isSuccessful) {
                    val latestDate = response.body()!!.string()

                    val sharedPref = SharedPreferenceUtil(context)
                    sharedPref.setLatestDate(latestDate)

                    Log.d("SplashModel", sharedPref.getLatestDate())

                    presenter.onSuccess()
                }
                else {
                    Log.e("SplashModel", "API response is not successful: ${response.errorBody()}")
                    presenter.onFailure()
                }
            }

            override fun onFailure(call: Call<ResponseBody>, t: Throwable) {
                Log.e("SplashModel", "API failure: ${t.stackTrace}")
                presenter.onFailure()
            }
        })
    }
}