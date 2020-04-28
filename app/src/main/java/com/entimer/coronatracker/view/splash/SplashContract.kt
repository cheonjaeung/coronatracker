package com.entimer.coronatracker.view.splash

import android.content.Context

interface SplashContract {
    interface View {
        fun onInitFinished()
        fun onInitFailed()
    }

    interface Presenter {
        fun initCountryList(context: Context)
    }
}