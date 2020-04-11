package io.github.entimer.coronatracker.ui.fragment

import android.app.Fragment
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import io.github.entimer.coronatracker.R
import kotlinx.android.synthetic.main.layout_card_worldwide.view.*
import java.text.DecimalFormat

class DashboardFragment: Fragment() {
    private var confirmed = 1696139
    private var recovered = 376200
    private var death = 102669

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater!!.inflate(R.layout.fragment_dashboard, container, false)

        val decimalFormat = DecimalFormat("###,###")
        view.dashboard_worldwide_confirmed.text = decimalFormat.format(confirmed)
        view.dashboard_worldwide_recovered.text = decimalFormat.format(recovered)
        view.dashboard_worldwide_death.text = decimalFormat.format(death)

        return view
    }
}