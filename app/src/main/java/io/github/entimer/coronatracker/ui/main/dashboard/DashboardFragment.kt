package io.github.entimer.coronatracker.ui.main.dashboard

import android.app.Fragment
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import io.github.entimer.coronatracker.R
import io.github.entimer.coronatracker.ui.base.IMvp
import io.github.entimer.coronatracker.util.dataclass.CaseData
import kotlinx.android.synthetic.main.card_global.view.*
import kotlinx.android.synthetic.main.fragment_dashboard.view.*
import java.text.DecimalFormat

class DashboardFragment: Fragment(), IMvp.View.Dashboard {
    private lateinit var presenter: DashboardPresenter

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater!!.inflate(R.layout.fragment_dashboard, container, false)

        presenter = DashboardPresenter(this, view)

        initViews(view)

        return view
    }

    override fun initViews(view: View) {
        startLoading(view)
        presenter.getData(view.context)
    }

    override fun initListeners(view: View) {

    }

    override fun updateCount(view: View, data: CaseData) {
        val format = DecimalFormat("###,###")

        val date = data.date
        val confirmed = data.confirmed
        val recovered = data.recovered
        val death = data.death
        val active = confirmed - recovered - death

        view.card_global_date.text = date
        view.card_global_confirmedNumber.text = format.format(confirmed)
        view.card_global_activeNumber.text = format.format(active)
        view.card_global_recoveredNumber.text = format.format(recovered)
        view.card_global_deathNumber.text = format.format(death)

        stopLoading(view)
    }

    override fun updatePieChart(view: View) {

    }

    override fun updateLineChart(view: View) {

    }

    override fun updateBarChart(view: View) {

    }

    override fun startLoading(view: View) {
        view.dashboard_loading.startAnimation(AnimationUtils.loadAnimation(view.context, R.anim.anim_updating))
        view.dashboard_loadingLayout.visibility = View.VISIBLE
    }

    override fun stopLoading(view: View) {
        view.dashboard_loading.clearAnimation()
        view.dashboard_loadingLayout.visibility = View.GONE
    }
}