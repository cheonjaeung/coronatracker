package com.entimer.coronatracker.ui.country

import android.content.Context
import com.entimer.coronatracker.api.covid.CaseData

class CountryPresenter(view: CountryActivity) {
    private val view = view
    private val model = CountryModel(this)

    fun getData(context: Context, countryCode: String) {
        view.startLoading()
        model.getData(context, countryCode)
    }

    fun updateData(caseList: ArrayList<CaseData>) {
        val size = caseList.size
        val countDataList = arrayListOf<CaseData>(caseList[size - 2], caseList[size - 1])
        val pieData = caseList[size - 1]

        view.updateCount(countDataList)
        view.updatePieChart(pieData)
        view.updateLineChart(caseList)

        view.stopLoading()
    }
}