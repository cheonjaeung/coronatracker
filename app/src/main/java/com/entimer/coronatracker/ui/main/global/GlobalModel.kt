package com.entimer.coronatracker.ui.main.global

import android.content.Context
import android.util.Log
import com.google.gson.JsonParser
import com.entimer.coronatracker.R
import com.entimer.coronatracker.api.covid.CovidApiService
import com.entimer.coronatracker.util.DateUtil
import com.entimer.coronatracker.api.covid.CaseData
import com.entimer.coronatracker.util.SharedPreferenceUtil
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import kotlin.collections.ArrayList

class GlobalModel(presenter: GlobalPresenter) {
    private val presenter = presenter
    private val logTag: String = "GlobalModel"
    val parser = JsonParser()

    fun getData(context: Context) {
        if(checkUpdatedDate(context)) {
            getDataFromApi()
        }
        else {
            getDataFromCache()
        }
    }

    private fun checkUpdatedDate(context: Context): Boolean {
        val today = DateUtil().getToday()
        val updated = SharedPreferenceUtil(context).getGlobalUpdated()

        val dateUtil = DateUtil()
        val difference = dateUtil.stringToCalendarWithTime(dateUtil.compare2Dates(today, updated)).timeInMillis
        if(difference > 3_600_000) {
            return true
        }
        return false
    }

    private fun getDataFromApi() {
        
    }

    private fun getDataFromCache() {

    }

    fun getEverydayCount(context: Context) {
        CovidApiService.getService().getGlobalEveryDay().enqueue(object: Callback<ResponseBody> {
            override fun onResponse(call: Call<ResponseBody>, response: Response<ResponseBody>) {
                if(response.isSuccessful) {
                    val body = response.body()!!.string()
                    val fullData = parser.parse(body).asJsonObject
                    val resultData = fullData["result"].asJsonObject
                    val itemCount = fullData["count"].asInt

                    var indexDate = context.getString(R.string.apiStartDate)
                    val caseList = ArrayList<CaseData>()
                    val dateUtil = DateUtil()
                    for(index in 0 until itemCount) {
                        val eachData = resultData[indexDate].asJsonObject
                        val confirmed = eachData["confirmed"].asInt
                        val death = eachData["deaths"].asInt
                        val recovered = eachData["recovered"].asInt
                        caseList.add(CaseData(indexDate, confirmed, recovered, death))
                        indexDate = dateUtil.getChangedDate(indexDate, 1)
                    }

                    val latestList = ArrayList<CaseData>()
                    latestList.add(caseList[caseList.size - 2])
                    latestList.add(caseList[caseList.size - 1])
                    presenter.updateCount(latestList)
                    presenter.updatePieChart(caseList[caseList.size - 1])
                    presenter.updateLineChart(caseList)
                }
                else {
                    Log.e(logTag, "API response is not successful: ${response.errorBody()}")
                }
            }

            override fun onFailure(call: Call<ResponseBody>, t: Throwable) {
                Log.e(logTag, "API failure:")
                t.printStackTrace()
            }
        })
    }
}