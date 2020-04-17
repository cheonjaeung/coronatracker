package io.github.entimer.coronatracker.ui.base

import android.content.Context

interface IMvp {
    interface View: IView {
        override fun initViews()
        override fun initListeners()
        fun onSuccessGetData()
        fun onFailureGetData()
        fun startUpdatingView()
        fun stopUpdatingView()
    }

    interface Presenter {
        fun getData(context: Context)
    }
}