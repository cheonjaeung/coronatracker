package com.entimer.coronatracker.view.main.summary

import com.entimer.coronatracker.data.dataclass.ApiCountryData
import com.entimer.coronatracker.data.dataclass.ApiRecentData
import com.entimer.coronatracker.util.api.CovidApiService
import com.entimer.coronatracker.util.apiCountryData2CovidData
import com.entimer.coronatracker.util.apiRecentData2CovidData
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class SummaryCardPresenter(view: SummaryCardContract.View): SummaryCardContract.Presenter {
    private val view = view

    override fun getGlobalData() {
        CovidApiService.getService().getRecentData().enqueue(object: Callback<ApiRecentData> {
            override fun onResponse(call: Call<ApiRecentData>, response: Response<ApiRecentData>) {
                if(response.isSuccessful) {
                    val apiData = response.body()!!
                    val data = apiRecentData2CovidData(apiData)
                    view.updateView(data)
                }
                else {

                }
            }

            override fun onFailure(call: Call<ApiRecentData>, t: Throwable) {

            }
        })
    }

    override fun getCountryData(country: String) {
        CovidApiService.getService().getCountriesData(country).enqueue(object: Callback<ApiCountryData> {
            override fun onResponse(call: Call<ApiCountryData>, response: Response<ApiCountryData>) {
                if(response.isSuccessful) {
                    val apiData = response.body()!!
                    val data = apiCountryData2CovidData(country, apiData)
                    view.updateView(data)
                }
                else {

                }
            }

            override fun onFailure(call: Call<ApiCountryData>, t: Throwable) {

            }
        })
    }
}