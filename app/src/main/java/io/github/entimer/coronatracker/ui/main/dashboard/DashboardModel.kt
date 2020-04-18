package io.github.entimer.coronatracker.ui.main.dashboard

import android.content.Context
import android.util.Log
import com.google.gson.JsonParser
import io.github.entimer.coronatracker.R
import io.github.entimer.coronatracker.api.covid.CovidApiService
import io.github.entimer.coronatracker.util.dataclass.CaseData
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList

class DashboardModel(presenter: DashboardPresenter) {
    private val presenter = presenter
    private val logTag: String = "DashboardModel"

    fun getData(context: Context) {
        getEverydayCount(context)
    }

    private fun getEverydayCount(context: Context) {
        CovidApiService.getService().getGlobalEveryDay().enqueue(object: Callback<ResponseBody> {
            override fun onResponse(call: Call<ResponseBody>, response: Response<ResponseBody>) {
                if(response.isSuccessful) {
                    val body = response.body()!!.string()
                    val parser = JsonParser()
                    val fullData = parser.parse(body).asJsonObject
                    val resultData = fullData["result"].asJsonObject
                    val itemCount = fullData["count"].asInt

                    var indexDate = context.getString(R.string.apiStartDate)
                    val caseList = ArrayList<CaseData>()
                    for(index in 0 until itemCount) {
                        val eachData = resultData[indexDate].asJsonObject
                        val confirmed = eachData["confirmed"].asInt
                        val death = eachData["deaths"].asInt
                        val recovered = eachData["recovered"].asInt
                        caseList.add(CaseData(indexDate, confirmed, recovered, death))
                        indexDate = getNextDate(indexDate)
                    }

                    val latestList = ArrayList<CaseData>()
                    latestList.add(caseList[caseList.size - 2])
                    latestList.add(caseList[caseList.size - 1])
                    presenter.updateCount(latestList)
                    presenter.updatePieChart(caseList[caseList.size - 1])
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

    private fun getNextDate(date: String): String {
        val splited = date.split("-")
        val year = splited[0].toInt()
        val month = splited[1].toInt()
        val date = splited[2].toInt()

        val calendar = Calendar.getInstance()
        calendar.set(Calendar.YEAR, year)
        calendar.set(Calendar.MONTH, month - 1)
        calendar.set(Calendar.DATE, date)

        calendar.add(Calendar.DATE, 1)

        val dateFormat = SimpleDateFormat("yyyy-MM-dd")
        return dateFormat.format(calendar.time)
    }
}