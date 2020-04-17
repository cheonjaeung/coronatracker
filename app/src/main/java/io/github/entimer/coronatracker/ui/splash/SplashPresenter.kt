package io.github.entimer.coronatracker.ui.splash

import android.content.Context
import io.github.entimer.coronatracker.ui.base.IMvp

class SplashPresenter(view: IMvp.View.Splash): IMvp.Presenter.Splash {
    private val view = view
    private val model = SplashModel(this)

    override fun getData(context: Context) {
        model.getLatestDate(context)
    }

    fun onSuccess() {
        view.onSuccessGetData()
    }

    fun onFailure() {
        view.onFailureGetData()
    }
}