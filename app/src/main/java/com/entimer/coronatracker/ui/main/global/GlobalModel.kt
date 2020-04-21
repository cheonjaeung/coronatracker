package com.entimer.coronatracker.ui.main.global

import android.content.Context
import android.util.Log
import com.google.gson.JsonParser
import com.entimer.coronatracker.api.covid.CovidApiService
import com.entimer.coronatracker.util.DateUtil
import com.entimer.coronatracker.api.covid.CaseData
import com.entimer.coronatracker.util.FileUtil
import com.entimer.coronatracker.util.SharedPreferenceUtil
import com.google.gson.JsonElement
import kotlinx.coroutines.*
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import kotlin.collections.ArrayList

class GlobalModel(presenter: GlobalPresenter) {
    private val presenter = presenter
    private val logTag: String = "GlobalModel"

    fun getData(context: Context) {
        if(checkUpdatedDate(context)) {
            getDataFromApi(context)
        }
        else {
            getDataFromCache(context)
        }
    }

    private fun checkUpdatedDate(context: Context): Boolean {
        val today = DateUtil().getToday()
        val updated = SharedPreferenceUtil(context).getGlobalUpdated()

        val difference = DateUtil().compare2Dates(today, updated)
        if(difference > 3_600_000) {
            return true
        }
        return false
    }

    private fun getDataFromApi(context: Context) {
        CovidApiService.getService().getGlobal().enqueue(object: Callback<ResponseBody> {
            override fun onResponse(call: Call<ResponseBody>, response: Response<ResponseBody>) {
                if (response.isSuccessful) {
                    val parser = JsonParser()
                    val body = response.body()!!.string()
                    Log.d(logTag, body)

                    val jsonBody = parser.parse(body).asJsonObject
                    val result = jsonBody["result"].asJsonObject
                    val entries: Set<Map.Entry<String, JsonElement>> = result.entrySet()

                    val dataList = ArrayList<CaseData>()
                    for (entry in entries) {
                        val date = entry.key
                        val value = entry.value.asJsonObject

                        val confirmed = value["confirmed"].asInt
                        val deaths = value["deaths"].asInt
                        val recovered = value["recovered"].asInt
                        dataList.add(CaseData(date, confirmed, recovered, deaths))
                    }

                    presenter.updateCount(arrayListOf(dataList[dataList.size - 2], dataList[dataList.size - 1]))
                    presenter.updatePieChart(dataList[dataList.size - 1])
                    presenter.updateLineChart(dataList)

                    val sf = SharedPreferenceUtil(context)
                    sf.setGlobalUpdated(DateUtil().getToday())

                    val fileUtil = FileUtil(context)
                    fileUtil.write(FileUtil.GLOBAL, body)
                } else {
                    Log.e(logTag, "API response is not successful: ${response.errorBody()}")
                }
            }

            override fun onFailure(call: Call<ResponseBody>, t: Throwable) {
                Log.e(logTag, "API request is failure:")
                t.printStackTrace()
            }
        })
    }

    private fun getDataFromCache(context: Context) {
        val fileUtil = FileUtil(context)
        var body = ""
        val dataList = ArrayList<CaseData>()

        GlobalScope.launch(Dispatchers.Main) {
            val read = async(Dispatchers.IO) {
                body = fileUtil.read(FileUtil.GLOBAL)
                Log.d(logTag, body)
            }

            read.await()
            val parser = JsonParser()
            val jsonBody = parser.parse(body).asJsonObject
            val result = jsonBody["result"].asJsonObject
            val entries: Set<Map.Entry<String, JsonElement>> = result.entrySet()

            for(entry in entries) {
                val date = entry.key
                val value = entry.value.asJsonObject

                val confirmed = value["confirmed"].asInt
                val deaths = value["deaths"].asInt
                val recovered = value["recovered"].asInt
                dataList.add(CaseData(date, confirmed, recovered, deaths))
            }

            presenter.updateCount(arrayListOf(dataList[dataList.size - 2], dataList[dataList.size - 1]))
            presenter.updatePieChart(dataList[dataList.size - 1])
            presenter.updateLineChart(dataList)
        }
    }
}