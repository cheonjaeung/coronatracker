package com.entimer.coronatracker.view.main

import com.entimer.coronatracker.data.dataclass.CovidData

interface MainContract {
    interface View {
        fun setRecentData(data: CovidData?)
    }

    interface Presenter {
        fun getRecentData()
    }
}