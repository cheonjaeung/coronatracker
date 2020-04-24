package com.entimer.coronatracker.ui.main.search

import android.content.Context
import android.util.Log
import android.view.View
import com.entimer.coronatracker.api.iso3166.Iso3166ApiService
import com.entimer.coronatracker.api.iso3166.Iso3166Data
import com.entimer.coronatracker.room.AppDatabase
import com.entimer.coronatracker.room.entity.Iso3166Entity
import com.entimer.coronatracker.util.SharedPreferenceUtil
import com.google.gson.JsonParser
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class SearchPresenter(view: SearchFragment, fragmentView: View) {
    private val view = view
    private val fragmentView = fragmentView
    private val logTag = "SearchPresenter"

    fun initData(context: Context) {
        view.startInitLoading(fragmentView)
        if(checkUpdated(context)) {
            onInitDataFinished()
        }
        else {
            getDataFromApi(context)
        }
    }

    private fun checkUpdated(context: Context): Boolean {
        val sf = SharedPreferenceUtil(context)
        return sf.getIso3166Updated()
    }

    private fun getDataFromApi(context: Context) {
        Iso3166ApiService.getService().getIso3166List().enqueue(object: Callback<ResponseBody> {
            override fun onResponse(call: Call<ResponseBody>, response: Response<ResponseBody>) {
                if(response.isSuccessful) {
                    val parser = JsonParser()
                    val body = response.body()!!.string()

                    val jsonBody = parser.parse(body).asJsonObject
                    val list = jsonBody["list"].asJsonArray

                    val dataList = ArrayList<Iso3166Data>()
                    for(item in list) {
                        val data = item.asJsonObject
                        val name = data["name"].asString
                        val alpha2 = data["alpha-2"].asString
                        val alpha3 = data["alpha-3"].asString
                        val numeric = data["numeric"].asString
                        dataList.add(Iso3166Data(name, alpha2, alpha3, numeric))
                    }

                    saveDataInDatabase(context, dataList)
                }
                else {
                    Log.e(logTag, "API response is not successful: ${response.errorBody()}")
                }
            }

            override fun onFailure(call: Call<ResponseBody>, t: Throwable) {
                Log.e(logTag, "API request is failure:")
                t.printStackTrace()
            }
        })
    }

    private fun saveDataInDatabase(context: Context, dataList: ArrayList<Iso3166Data>) {
        val db = AppDatabase.getDatabase(context)
        val sf = SharedPreferenceUtil(context)

        GlobalScope.launch(Dispatchers.Main) {
            val save = async(Dispatchers.IO) {
                for(data in dataList) {
                    val entity = Iso3166Entity(data.name, data.alpha2, data.alpha3, data.numeric)
                    db.iso3166Dao().insert(entity)
                }
            }

            save.await()
            sf.setIso3166Updated(true)
            onInitDataFinished()
        }
    }

    private fun onInitDataFinished() {
        view.stopInitLoading(fragmentView)
    }

    fun getData(context: Context, keyword: String) {
        view.startLoading(fragmentView)
        getDataFromDatabase(context, keyword)
    }

    private fun getDataFromDatabase(context: Context, keyword: String) {

        val db = AppDatabase.getDatabase(context)
        val dataList = ArrayList<Iso3166Data>()

        GlobalScope.launch(Dispatchers.Main) {
            val get = async(Dispatchers.IO) {
                val entityList = db.iso3166Dao().selectByKeyword("%$keyword%")
                for(entity in entityList) {
                    val name = entity.name
                    val alpha2 = entity.alpha2
                    val alpha3 = entity.alpha3
                    val numeric = entity.numeric
                    dataList.add(Iso3166Data(name, alpha2, alpha3, numeric))
                }
            }

            get.await()
            onGetDataFinished(dataList)
        }
    }

    private fun onGetDataFinished(dataList: ArrayList<Iso3166Data>) {
        view.updateSearchList(dataList)
        view.stopLoading(fragmentView)
    }
}