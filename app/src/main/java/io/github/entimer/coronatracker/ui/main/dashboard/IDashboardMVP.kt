package io.github.entimer.coronatracker.ui.main.dashboard

import android.content.Context

interface IDashboardMVP {
    interface View {
        fun updateView()
    }

    interface Presenter {
        fun getData(context: Context)
    }
}