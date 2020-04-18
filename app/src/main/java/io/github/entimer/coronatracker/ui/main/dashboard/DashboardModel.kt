package io.github.entimer.coronatracker.ui.main.dashboard

import android.content.Context
import android.util.Log
import com.google.gson.JsonParser
import io.github.entimer.coronatracker.R
import io.github.entimer.coronatracker.api.covid.CovidApiService
import io.github.entimer.coronatracker.util.DateUtil
import io.github.entimer.coronatracker.api.covid.CaseData
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import kotlin.collections.ArrayList

class DashboardModel(presenter: DashboardPresenter) {
    private val presenter = presenter
    private val logTag: String = "DashboardModel"
    val parser = JsonParser()

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
                Log.e(logTag, "API failure: ${t.stackTrace}")
            }
        })
    }

    fun getEveryCountriesCount(context: Context) {
        CovidApiService.getService().getGlobalEveryCountries().enqueue(object: Callback<ResponseBody> {
            override fun onResponse(call: Call<ResponseBody>, response: Response<ResponseBody>) {
                if(response.isSuccessful) {
                    val body = response.body()!!.string()
                    val fullData = parser.parse(body).asJsonObject
                    val resultData = fullData["result"].asJsonArray
                    val itemCount = fullData["count"].asInt

                    for(data in resultData) {
                        Log.d(logTag, "$data")
                    }
                    presenter.updateBarChart()
                }
                else {
                    Log.e(logTag, "API response is not successful: ${response.errorBody()}")
                }
            }

            override fun onFailure(call: Call<ResponseBody>, t: Throwable) {
                Log.e(logTag, "API failure: ${t.stackTrace}")
            }
        })
    }
}