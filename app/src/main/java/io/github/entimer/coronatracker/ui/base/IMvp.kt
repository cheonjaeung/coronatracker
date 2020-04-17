package io.github.entimer.coronatracker.ui.base

import android.content.Context
import android.view.View
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
            fun updateCount(view: android.view.View, data: CaseData)
            fun updatePieChart(view: android.view.View)
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
            fun updateCount(data: CaseData)
            fun updatePieChart()
            fun updateLineChart()
            fun updateBarChart()
        }
    }
}