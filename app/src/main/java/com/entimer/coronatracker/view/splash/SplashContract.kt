package com.entimer.coronatracker.view.splash

import com.entimer.coronatracker.data.dataclass.ApiCountryData
import com.entimer.coronatracker.data.dataclass.ApiRecentData

interface SplashContract {
    interface View {
        var isRecentDataFinished: Boolean
        var isCountryDataFinished: Boolean

        fun onSuccess()
        fun onFailure()
    }

    interface Presenter {
        fun getRecentData()
        fun setRecentData(data: ApiRecentData)
        fun getCountryData()
        fun setCountryData(data: ApiCountryData)
    }
}