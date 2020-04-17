package io.github.entimer.coronatracker.ui.main.dashboard

import android.content.Context

class DashboardPresenter(view: IDashboardMVP.View): IDashboardMVP.Presenter {
    private val view = view
    private val model = DashboardModel(this)

    fun updateView() {
        view.updateView()
    }

    override fun getData(context: Context) {

    }
}