package com.entimer.coronatracker.ui.main.global

import android.content.Context
import android.util.Log
import android.view.View
import com.entimer.coronatracker.api.covid.CaseData
import com.entimer.coronatracker.api.covid.CovidApiService
import com.entimer.coronatracker.util.DateUtil
import com.entimer.coronatracker.util.FileUtil
import com.entimer.coronatracker.util.SharedPreferenceUtil
import com.google.gson.JsonElement
import com.google.gson.JsonParser
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class GlobalPresenter(view: GlobalFragment, fragmentView: View) {
    private val view = view
    private val fragmentView = fragmentView
    private val logTag = "GlobalPresenter"

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

                    updateView(dataList)

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

            updateView(dataList)
        }
    }

    fun updateView(dataList: ArrayList<CaseData>) {
        val size = dataList.size
        val countData = arrayListOf<CaseData>(dataList[size - 2], dataList[size - 1])

        view.updateCount(fragmentView, countData)
        view.updatePieChart(fragmentView, dataList[size - 1])
        view.updateLineChart(fragmentView, dataList)
        view.stopLoading(fragmentView)
    }
}
