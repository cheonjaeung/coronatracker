package io.github.entimer.coronatracker.ui.fragment

import android.app.Fragment
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import io.github.entimer.coronatracker.R
import kotlinx.android.synthetic.main.layout_card_worldwide.view.*

class DashboardFragment: Fragment() {
    private var confirmed = 1696139
    private var recovered = 376200
    private var death = 102669

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater!!.inflate(R.layout.fragment_dashboard, container, false)

        view.dashboard_worldwide_confirmed.text = confirmed.toString()
        view.dashboard_worldwide_recovered.text = recovered.toString()
        view.dashboard_worldwide_death.text = death.toString()

        return view
    }
}