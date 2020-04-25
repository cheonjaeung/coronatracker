package com.entimer.coronatracker.view.splash

import android.content.Context
import com.entimer.coronatracker.data.dataclass.ApiRecentData
import com.entimer.coronatracker.data.room.CoronaTrackerRoom
import com.entimer.coronatracker.util.api.CovidApiService
import com.entimer.coronatracker.util.apiRecentData2RecentEntry
import kotlinx.coroutines.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.lang.Exception

class SplashPresenter(context: Context, view: SplashContract.View): SplashContract.Presenter {
    private val context = context
    private val view = view

    override fun getRecentData() {
        CovidApiService.getService().getRecentData().enqueue(object: Callback<ApiRecentData> {
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
                view.onSuccess()
            }
            else {
                view.onFailure()
            }
        }
    }
}