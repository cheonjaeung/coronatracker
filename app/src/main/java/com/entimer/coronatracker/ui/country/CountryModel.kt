package com.entimer.coronatracker.ui.country

import android.content.Context
import android.util.Log
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

class CountryModel(presenter: CountryPresenter) {
    private val presenter = presenter
    private val logTag = "CountryModel"

    fun getData(context: Context, countryCode: String) {
        if(checkUpdatedDate(context, countryCode)) {
            getDataFromApi(context, countryCode)
        }
        else {
            getDataFromCache(context, countryCode)
        }
    }

    private fun checkUpdatedDate(context: Context, countryCode: String): Boolean {
        val today = DateUtil().getToday()
        val updated = SharedPreferenceUtil(context).getCountryUpdated(countryCode)

        val difference = DateUtil().compare2Dates(today, updated)
        if(difference > 3_600_000) {
            return true
        }
        return false
    }

    private fun getDataFromApi(context: Context, countryCode: String) {
        CovidApiService.getService().getCountry(countryCode).enqueue(object: Callback<ResponseBody> {
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

                    presenter.updateData(dataList)

                    val sf = SharedPreferenceUtil(context)
                    sf.setCountryUpdated(DateUtil().getToday(), countryCode)

                    val fileUtil = FileUtil(context)
                    fileUtil.write("$countryCode.json", body)
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

    private fun getDataFromCache(context: Context, countryCode: String) {
        val fileUtil = FileUtil(context)
        var body = ""
        val dataList = ArrayList<CaseData>()

        GlobalScope.launch(Dispatchers.Main) {
            val read = async(Dispatchers.IO) {
                body = fileUtil.read("$countryCode.json")
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

            presenter.updateData(dataList)
        }
    }
}