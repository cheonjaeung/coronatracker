package com.entimer.coronatracker.view.splash

import com.entimer.coronatracker.data.dataclass.ApiRecentData

interface SplashContract {
    interface View {
        fun onSuccess()
        fun onFailure()
    }

    interface Presenter {
        fun getRecentData()
        fun setRecentData(data: ApiRecentData)
    }
}