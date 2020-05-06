package com.entimer.coronatracker.view.newcard

import android.content.Context
import com.entimer.coronatracker.data.dataclass.CountryData
import com.entimer.coronatracker.data.room.CoronaTrackerRoom
import com.entimer.coronatracker.util.countryEntity2CountryData
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import java.lang.Exception

class NewCardPresenter(view: NewCardContract.View): NewCardContract.Presenter {
    private val view = view

    override fun getCountryList(context: Context, keyword: String) {
        GlobalScope.launch(Dispatchers.Main) {
            val data = ArrayList<CountryData>()

            val getData = async(Dispatchers.IO) {
                try {
                    val db = CoronaTrackerRoom.getDatabase(context)
                    val entities = db.countryDao().select("%$keyword%")
                    for (entity in entities) {
                        data.add(countryEntity2CountryData(entity))
                    }
                    true
                } catch (e: Exception) {
                    e.printStackTrace()
                    false
                }
            }

            if (getData.await()) {
                view.setList(data)
            }
        }
    }
}