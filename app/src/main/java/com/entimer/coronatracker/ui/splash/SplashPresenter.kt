package com.entimer.coronatracker.ui.splash

import android.content.Context

class SplashPresenter(view: SplashActivity) {
    private val view = view
    private val model = SplashModel(this)

    fun getData(context: Context) {
        model.getIso3166Codes(context)
    }

    fun onSuccess() {
        view.onSuccessGetData()
    }

    fun onFailure() {
        view.onFailureGetData()
    }
}