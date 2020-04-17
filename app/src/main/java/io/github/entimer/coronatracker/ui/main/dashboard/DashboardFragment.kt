package io.github.entimer.coronatracker.ui.main.dashboard

import android.app.Fragment
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import io.github.entimer.coronatracker.R
import io.github.entimer.coronatracker.ui.IView

class DashboardFragment: Fragment(), IView.Fragment, IDashboardMVP.View {
    private lateinit var presenter: DashboardPresenter

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater!!.inflate(R.layout.fragment_dashboard, container, false)

        presenter = DashboardPresenter(this)

        initView(view)
        initListener(view)

        return view
    }

    override fun initView(view: View) {

    }

    override fun initListener(view: View) {

    }

    override fun updateView() {

    }
}