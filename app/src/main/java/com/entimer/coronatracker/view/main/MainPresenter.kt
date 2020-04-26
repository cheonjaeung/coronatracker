package com.entimer.coronatracker.view.main

import android.content.Context
import com.entimer.coronatracker.data.dataclass.CovidData
import com.entimer.coronatracker.data.room.CoronaTrackerRoom
import com.entimer.coronatracker.util.recentEntry2CovidData
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import java.lang.Exception

class MainPresenter(context: Context, view: MainContract.View): MainContract.Presenter {
    private val context = context
    private val view = view

    private val db = CoronaTrackerRoom.getDatabase(context)

    override fun getRecentData() {
        GlobalScope.launch(Dispatchers.Main) {
            var data: CovidData? = null

            val read = async(Dispatchers.IO) {
                try {
                    data = recentEntry2CovidData(db.recentDao().select())
                    true
                }
                catch(e: Exception) {
                    e.printStackTrace()
                    false
                }
            }

            if(read.await()) {
                view.setRecentData(data)
            }
            else {
                view.setRecentData(null)
            }
        }
    }
}