package io.github.entimer.coronatracker.ui.main.dashboard

import android.app.Fragment
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import io.github.entimer.coronatracker.R
import io.github.entimer.coronatracker.ui.base.IMvp

class DashboardFragment: Fragment(), IMvp.View {
    private lateinit var presenter: DashboardPresenter

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater!!.inflate(R.layout.fragment_dashboard, container, false)

        presenter = DashboardPresenter(this)

        return view
    }

    override fun initViews() {

    }

    override fun initListeners() {

    }

    override fun onSuccessGetData() {

    }

    override fun onFailureGetData() {

    }

    override fun startUpdatingView() {

    }

    override fun stopUpdatingView() {

    }
}