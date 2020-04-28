package com.entimer.coronatracker.view.main.add.addcard

import com.entimer.coronatracker.data.dataclass.CountryData
import com.entimer.coronatracker.util.api.ApiCountryListData
import com.entimer.coronatracker.util.api.CovidApiService
import com.entimer.coronatracker.util.apiCountryListDataCountry2CountryData
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class AddCardPresenter(view: AddCardContract.View): AddCardContract.Presenter {
    private val view = view

    override fun getCountryList() {
        CovidApiService.getService().getCountriesList().enqueue(object: Callback<ApiCountryListData> {
            override fun onResponse(call: Call<ApiCountryListData>, response: Response<ApiCountryListData>) {
                if(response.isSuccessful) {
                    val apiData = response.body()!!
                    val data = ArrayList<CountryData>()

                    for(item in apiData.countries) {
                        data.add(apiCountryListDataCountry2CountryData(item))
                    }

                    view.allCountries = data
                }

                else {

                }
            }

            override fun onFailure(call: Call<ApiCountryListData>, t: Throwable) {

            }
        })
    }
}