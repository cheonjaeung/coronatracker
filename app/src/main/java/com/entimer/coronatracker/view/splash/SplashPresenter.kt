package com.entimer.coronatracker.view.splash

import android.content.Context
import android.util.Log
import com.entimer.coronatracker.data.room.CoronaTrackerRoom
import com.entimer.coronatracker.util.SharedPreferencesUtil
import com.entimer.coronatracker.util.api.ApiCountryListData
import com.entimer.coronatracker.util.api.ApiCountryListDataCountry
import com.entimer.coronatracker.util.api.CovidApiService
import com.entimer.coronatracker.util.apiCountryListDataCountry2CountryEntity
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.lang.Exception

class SplashPresenter(view: SplashContract.View): SplashContract.Presenter {
    private val view = view

    override fun initCountryList(context: Context) {
        val sf = SharedPreferencesUtil(context)
        if(sf.getBoolean(SharedPreferencesUtil.KEY_COUNTRY_LIST_INITIALIZED, SharedPreferencesUtil.DEFAULT_COUNTRY_LIST_INITIALIZED)) {
            view.onInitFinished()
        }
        else {
            CovidApiService.getService().getCountriesList().enqueue(object: Callback<ApiCountryListData> {
                override fun onResponse(call: Call<ApiCountryListData>, response: Response<ApiCountryListData>) {
                    if(response.isSuccessful) {
                        val apiData = response.body()!!
                        saveCountryList(context, apiData.countries)
                    }
                    else {
                        view.onInitFailed()
                    }
                }

                override fun onFailure(call: Call<ApiCountryListData>, t: Throwable) {
                    t.printStackTrace()
                    view.onInitFailed()
                }
            })
        }
    }

    private fun saveCountryList(context: Context, data: ArrayList<ApiCountryListDataCountry>) {
        Log.d("test", "$data")
        GlobalScope.launch(Dispatchers.Main) {
            val save = async(Dispatchers.IO) {
                try {
                    val db = CoronaTrackerRoom.getDatabase(context)

                    for(item in data)
                        db.countryDao().insert(apiCountryListDataCountry2CountryEntity(item))

                    true
                }
                catch(e: Exception) {
                    e.printStackTrace()
                    false
                }
            }

            if(save.await()) {
                val sf = SharedPreferencesUtil(context)
                sf.setBoolean(SharedPreferencesUtil.KEY_COUNTRY_LIST_INITIALIZED, true)
                view.onInitFinished()
            }
            else {
                view.onInitFailed()
            }
        }
    }
}