package io.github.entimer.coronatracker.ui.main

import android.content.Context

interface IMainMVP {
    interface View {
    }

    interface Presenter {
        fun getData(context: Context)
    }
}