package com.entimer.coronatracker.view.splash

import android.content.Context
import com.entimer.coronatracker.data.dataclass.ApiCountryData
import com.entimer.coronatracker.data.dataclass.ApiRecentData
import com.entimer.coronatracker.data.room.CoronaTrackerRoom
import com.entimer.coronatracker.util.api.CovidApiService
import com.entimer.coronatracker.util.apiCountryData2CountryEntity
import com.entimer.coronatracker.util.apiRecentData2RecentEntry
import kotlinx.coroutines.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.lang.Exception

class SplashPresenter(context: Context, view: SplashContract.View): SplashContract.Presenter {
    private val context = context
    private val view = view

    private val apiService = CovidApiService.getService()

    override fun getRecentData() {
        apiService.getRecentData().enqueue(object: Callback<ApiRecentData> {
            override fun onResponse(call: Call<ApiRecentData>, response: Response<ApiRecentData>) {
                if(response.isSuccessful) {
                    val apiData = response.body()!!
                    setRecentData(apiData)
                }
                else {
                    view.onFailure()
                }
            }

            override fun onFailure(call: Call<ApiRecentData>, t: Throwable) {
                view.onFailure()
            }
        })
    }

    override fun setRecentData(data: ApiRecentData) {
        GlobalScope.launch(Dispatchers.Main) {
            val save = async(Dispatchers.IO) {
                try {
                    val db = CoronaTrackerRoom.getDatabase(context)
                    db.recentDao().insert(apiRecentData2RecentEntry(data))
                    true
                }
                catch(e: Exception) {
                    e.printStackTrace()
                    false
                }
            }

            if(save.await()) {
                view.isRecentDataFinished = true
                view.onSuccess()
            }
            else {
                view.onFailure()
            }
        }
    }

    override fun getCountryData() {
        apiService.getCountriesData().enqueue(object: Callback<ApiCountryData> {
            override fun onResponse(call: Call<ApiCountryData>, response: Response<ApiCountryData>) {
                if(response.isSuccessful) {
                    val apiData = response.body()!!
                    setCountryData(apiData)
                }
                else {
                    view.onFailure()
                }
            }

            override fun onFailure(call: Call<ApiCountryData>, t: Throwable) {
                view.onFailure()
            }
        })
    }

    override fun setCountryData(data: ApiCountryData) {
        GlobalScope.launch(Dispatchers.Main) {
            val save = async(Dispatchers.IO) {
                try {
                    val db = CoronaTrackerRoom.getDatabase(context)
                    val dataList = apiCountryData2CountryEntity(data)
                    for(item in dataList) {
                        db.countryDao().insert(item)
                    }
                    true
                }
                catch(e: Exception) {
                    e.printStackTrace()
                    false
                }
            }

            if(save.await()) {
                view.isCountryDataFinished = true
                view.onSuccess()
            }
            else {
                view.onFailure()
            }
        }
    }
}