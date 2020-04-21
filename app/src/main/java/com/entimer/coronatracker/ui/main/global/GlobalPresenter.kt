package com.entimer.coronatracker.ui.main.global

import android.content.Context
import android.view.View
import com.entimer.coronatracker.api.covid.CaseData

class GlobalPresenter(view: GlobalFragment, fragmentView: View) {
    private val view = view
    private val fragmentView = fragmentView
    private val model = GlobalModel(this)

    fun getData(context: Context) {
        model.getEverydayCount(context)
    }

    fun updateCount(caseList: ArrayList<CaseData>) {
        view.updateCount(fragmentView, caseList)
    }

    fun updatePieChart(caseData: CaseData) {
        view.updatePieChart(fragmentView, caseData)
    }

    fun updateLineChart(caseList: ArrayList<CaseData>) {
        view.updateLineChart(fragmentView, caseList)
    }
}
