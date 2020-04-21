package com.entimer.coronatracker.ui.main.global

import android.content.Context
import android.view.View
import com.entimer.coronatracker.api.covid.CaseData

class GlobalPresenter(view: GlobalFragment, fragmentView: View) {
    private val view = view
    private val fragmentView = fragmentView
    private val model = GlobalModel(this)

    private var isCountFinished = false
    private var isPieFinished = false
    private var isLineFinished = false

    fun getData(context: Context) {
        view.startLoading(fragmentView)
        model.getData(context)
    }

    fun updateCount(caseList: ArrayList<CaseData>) {
        view.updateCount(fragmentView, caseList)
        isCountFinished = true
        checkAllUpdated()
    }

    fun updatePieChart(caseData: CaseData) {
        view.updatePieChart(fragmentView, caseData)
        isPieFinished = true
        checkAllUpdated()
    }

    fun updateLineChart(caseList: ArrayList<CaseData>) {
        view.updateLineChart(fragmentView, caseList)
        isLineFinished = true
        checkAllUpdated()
    }

    private fun checkAllUpdated() {
        if(isCountFinished && isPieFinished && isLineFinished) {
            view.stopLoading(fragmentView)
        }
    }
}
