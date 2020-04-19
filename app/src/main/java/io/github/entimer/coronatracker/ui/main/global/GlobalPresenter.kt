package io.github.entimer.coronatracker.ui.main.global

import android.content.Context
import android.view.View
import io.github.entimer.coronatracker.ui.base.IMvp
import io.github.entimer.coronatracker.api.covid.CaseData

class GlobalPresenter(view: IMvp.View.Global, fragmentView: View): IMvp.Presenter.Global {
    private val view = view
    private val fragmentView = fragmentView
    private val model = GlobalModel(this)

    override fun getData(context: Context) {
        model.getEverydayCount(context)
    }

    override fun updateCount(caseList: ArrayList<CaseData>) {
        view.updateCount(fragmentView, caseList)
    }

    override fun updatePieChart(caseData: CaseData) {
        view.updatePieChart(fragmentView, caseData)
    }

    override fun updateLineChart(caseList: ArrayList<CaseData>) {
        view.updateLineChart(fragmentView, caseList)
    }
}
