package io.github.entimer.coronatracker.ui.base

import android.content.Context
import io.github.entimer.coronatracker.util.dataclass.CaseData

interface IMvp {
    interface View {
        interface Splash: IView {
            override fun initViews()
            override fun initListeners()
            fun onSuccessGetData()
            fun onFailureGetData()
            fun startLoading()
            fun stopLoading()
        }

        interface Dashboard {
            fun initViews(view: android.view.View)
            fun initListeners(view: android.view.View)
            fun updateCount(view: android.view.View, caseList: ArrayList<CaseData>)
            fun updatePieChart(view: android.view.View, caseData: CaseData)
            fun updateLineChart(view: android.view.View)
            fun updateBarChart(view: android.view.View)
            fun startLoading(view: android.view.View)
            fun stopLoading(view: android.view.View)
        }
    }

    interface Presenter {
        interface Splash {
            fun getData(context: Context)
        }

        interface Dashboard {
            fun getData(context: Context)
            fun updateCount(caseList: ArrayList<CaseData>)
            fun updatePieChart(caseData: CaseData)
            fun updateLineChart()
            fun updateBarChart()
        }
    }
}