package io.github.entimer.coronatracker.ui.main.dashboard

import android.content.Context
import android.util.Log
import com.google.gson.JsonParser
import io.github.entimer.coronatracker.api.covid.CovidApiService
import io.github.entimer.coronatracker.util.SharedPreferenceUtil
import io.github.entimer.coronatracker.util.dataclass.CaseData
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class DashboardModel(presenter: DashboardPresenter) {
    private val presenter = presenter

    fun getGlobalCount(context: Context) {
        val sharedPref = SharedPreferenceUtil(context)
        val latestDate = sharedPref.getLatestDate()

        CovidApiService.getService().getGlobal(latestDate).enqueue(object: Callback<ResponseBody> {
            override fun onResponse(call: Call<ResponseBody>, response: Response<ResponseBody>) {
                if(response.isSuccessful) {
                    val body = response.body()!!.string()

                    val parser = JsonParser()
                    val fullData = parser.parse(body).asJsonObject
                    val date = fullData.get("date").asString
                    val resultData = fullData.get("result").asJsonObject
                    val confirmed = resultData.get("confirmed").asInt
                    val death = resultData.get("deaths").asInt
                    val recovered = resultData.get("recovered").asInt
                    val case = CaseData(date, confirmed, recovered, death)

                    presenter.updateCount(case)

                    Log.d("DashboardModel", body)
                }
                else {
                    Log.e("DashboardModel", "API response is not successful: ${response.errorBody()}")
                }
            }

            override fun onFailure(call: Call<ResponseBody>, t: Throwable) {
                Log.e("DashboardModel", "API failure: ${t.stackTrace}")
            }
        })
    }
}