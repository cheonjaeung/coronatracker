package io.github.entimer.coronatracker.ui.main.dashboard

import android.content.Context
import io.github.entimer.coronatracker.ui.base.IMvp

class DashboardPresenter(view: IMvp.View): IMvp.Presenter {
    private val view = view
    private val model = DashboardModel(this)

    override fun getData(context: Context) {

    }
}
