package com.entimer.coronatracker.view.splash

import com.entimer.coronatracker.data.dataclass.ApiRecentData

interface SplashContract {
    interface View {
        fun getData()
        fun onGetDataFinished()
        fun onGetDataFailed()
    }

    interface Presenter {
        fun getRecentData()
        fun setRecentData(data: ApiRecentData)
    }
}