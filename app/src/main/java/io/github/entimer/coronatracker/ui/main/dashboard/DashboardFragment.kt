package io.github.entimer.coronatracker.ui.main.dashboard

import android.app.Fragment
import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import com.github.mikephil.charting.components.Legend
import com.github.mikephil.charting.components.XAxis
import com.github.mikephil.charting.data.*
import com.github.mikephil.charting.formatter.LargeValueFormatter
import com.github.mikephil.charting.formatter.PercentFormatter
import io.github.entimer.coronatracker.R
import io.github.entimer.coronatracker.ui.base.IMvp
import io.github.entimer.coronatracker.util.DateValueFormatter
import io.github.entimer.coronatracker.api.covid.CaseData
import kotlinx.android.synthetic.main.card_global.view.*
import kotlinx.android.synthetic.main.fragment_dashboard.view.*
import java.text.DecimalFormat

class DashboardFragment: Fragment(), IMvp.View.Dashboard {
    private lateinit var presenter: DashboardPresenter

    private var isCountFinished = false
    private var isPieFinished = false
    private var isLineFinished = false
    private var isBarFinished = false

    private var confirmedColor = 0
    private var activeColor = 0
    private var recoveredColor = 0
    private var deathColor = 0

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater!!.inflate(R.layout.fragment_dashboard, container, false)

        presenter = DashboardPresenter(this, view)

        initViews(view)

        return view
    }

    override fun initViews(view: View) {
        startLoading(view)
        confirmedColor = view.context.resources.getColor(R.color.colorConfirmed)
        activeColor = view.context.resources.getColor(R.color.colorActive)
        recoveredColor = view.context.resources.getColor(R.color.colorRecovered)
        deathColor = view.context.resources.getColor(R.color.colorDeath)
        initPieChart(view)
        initLineChart(view)
        initBarChart(view)
        presenter.getData(view.context)
    }

    private fun initPieChart(view: View) {
        val chart = view.card_global_pieChart
        chart.setUsePercentValues(true)
        chart.description.isEnabled = false
        chart.dragDecelerationFrictionCoef = 0.5f
        chart.holeRadius = 55f
        chart.transparentCircleRadius = 60f
        chart.rotationAngle = 0f
        chart.isRotationEnabled = true
        chart.isHighlightPerTapEnabled = true
        chart.centerText = view.context.getString(R.string.globalPieChartLabel)
        chart.setCenterTextColor(view.context.resources.getColor(R.color.colorText))
        chart.setHoleColor(view.context.resources.getColor(R.color.colorBackground))
        chart.setTransparentCircleColor(view.context.resources.getColor(R.color.colorBackground))

        val legend = chart.legend
        legend.horizontalAlignment = Legend.LegendHorizontalAlignment.CENTER
        legend.verticalAlignment = Legend.LegendVerticalAlignment.TOP
        legend.orientation = Legend.LegendOrientation.HORIZONTAL
        legend.xEntrySpace = 12f
        legend.textColor = view.context.resources.getColor(R.color.colorText)
    }

    private fun initLineChart(view: View) {
        val chart = view.card_global_lineChart
        chart.setExtraOffsets(0f, 10f, 0f, 0f)
        chart.description.isEnabled = false
        chart.setTouchEnabled(true)
        chart.isDragEnabled = true
        chart.isScaleXEnabled = false
        chart.isScaleYEnabled = true
        chart.isDoubleTapToZoomEnabled = false

        val legend = chart.legend
        legend.horizontalAlignment = Legend.LegendHorizontalAlignment.CENTER
        legend.verticalAlignment = Legend.LegendVerticalAlignment.TOP
        legend.orientation = Legend.LegendOrientation.HORIZONTAL
        legend.xEntrySpace = 12f
        legend.textColor = view.context.resources.getColor(R.color.colorText)

        val axisX = chart.xAxis
        axisX.position = XAxis.XAxisPosition.BOTTOM
        axisX.textColor = view.context.resources.getColor(R.color.colorText)
        axisX.valueFormatter = DateValueFormatter(view.context)

        val axisLeft = chart.axisLeft
        axisLeft.axisMinimum = 0f
        axisLeft.textColor = view.context.resources.getColor(R.color.colorText)
        axisLeft.valueFormatter = LargeValueFormatter()

        val axisRight = chart.axisRight
        axisRight.isEnabled = false
    }

    private fun initBarChart(view: View) {
        val chart = view.card_global_barChart
        chart.setExtraOffsets(0f, 10f, 0f, 0f)
        chart.description.isEnabled = false
        chart.setTouchEnabled(true)
        chart.isDragEnabled = true
        chart.setScaleEnabled(false)
        chart.isDoubleTapToZoomEnabled = false

        val legend = chart.legend
        legend.horizontalAlignment = Legend.LegendHorizontalAlignment.CENTER
        legend.verticalAlignment = Legend.LegendVerticalAlignment.TOP
        legend.orientation = Legend.LegendOrientation.HORIZONTAL
        legend.xEntrySpace = 12f
        legend.textColor = view.context.resources.getColor(R.color.colorText)

        val axisX = chart.xAxis
        axisX.position = XAxis.XAxisPosition.BOTTOM
        axisX.textColor = view.context.resources.getColor(R.color.colorText)

        val axisLeft = chart.axisLeft
        axisLeft.axisMinimum = 0f
        axisLeft.textColor = view.context.resources.getColor(R.color.colorText)
        axisLeft.valueFormatter = LargeValueFormatter()

        val axisRight = chart.axisRight
        axisRight.isEnabled = false
    }

    override fun initListeners(view: View) {

    }

    override fun updateCount(view: View, caseList: ArrayList<CaseData>) {
        val numFormat = DecimalFormat("###,###")
        val floatFormat = DecimalFormat(".##")

        val confirmed = caseList[1].confirmed
        val recovered = caseList[1].recovered
        val death = caseList[1].death
        val active = confirmed - recovered - death

        val confirmedIncrease = confirmed - caseList[0].confirmed
        val yesterdayActive = caseList[0].confirmed - caseList[0].recovered - caseList[0].death
        val activeIncrease = active - yesterdayActive
        val recoveredIncrease = recovered - caseList[0].recovered
        val deathIncrease = death - caseList[0].death

        val confirmedRate = (confirmedIncrease.toFloat() / caseList[0].confirmed.toFloat()) * 100f
        val activeRate = (activeIncrease.toFloat() / yesterdayActive.toFloat()) * 100f
        val recoveredRate = (recoveredIncrease.toFloat() / caseList[0].recovered.toFloat()) * 100f
        val deathRate = (deathIncrease.toFloat() / caseList[0].death.toFloat()) * 100f

        view.card_global_date.text = caseList[1].date

        view.card_global_confirmedNumber.text = numFormat.format(confirmed)
        view.card_global_activeNumber.text = numFormat.format(active)
        view.card_global_recoveredNumber.text = numFormat.format(recovered)
        view.card_global_deathNumber.text = numFormat.format(death)

        view.card_global_confirmedIncrease.text = "+${numFormat.format(confirmedIncrease)}"
        view.card_global_activeIncrease.text = "+${numFormat.format(activeIncrease)}"
        view.card_global_recoveredIncrease.text = "+${numFormat.format(recoveredIncrease)}"
        view.card_global_deathIncrease.text = "+${numFormat.format(deathIncrease)}"

        view.card_global_confirmedIncreaseRate.text = "+${floatFormat.format(confirmedRate)}%"
        view.card_global_activeIncreaseRate.text = "+${floatFormat.format(activeRate)}%"
        view.card_global_recoveredIncreaseRate.text = "+${floatFormat.format(recoveredRate)}%"
        view.card_global_deathIncreaseRate.text = "+${floatFormat.format(deathRate)}%"

        isCountFinished = true
        stopLoading(view)
    }

    override fun updatePieChart(view: View, caseData: CaseData) {
        val confirmed = caseData.confirmed
        val recovered = caseData.recovered
        val death = caseData.death
        val active = confirmed - recovered - death

        val entries = ArrayList<PieEntry>()
        entries.add(PieEntry(active.toFloat(), view.context.getString(R.string.active)))
        entries.add(PieEntry(recovered.toFloat(), view.context.getString(R.string.recovered)))
        entries.add(PieEntry(death.toFloat(), view.context.getString(R.string.death)))

        val dataSet = PieDataSet(entries, "")
        dataSet.sliceSpace = 3f
        dataSet.selectionShift = 6f
        val colors = ArrayList<Int>()
        colors.add(activeColor)
        colors.add(recoveredColor)
        colors.add(deathColor)
        dataSet.colors = colors

        val data = PieData(dataSet)
        data.setValueFormatter(PercentFormatter(view.card_global_pieChart))
        data.setValueTextSize(16f)
        data.setValueTextColor(Color.WHITE)
        view.card_global_pieChart.data = data

        view.card_global_pieChart.invalidate()
        view.card_global_pieChart.animateY(1000)

        isPieFinished = true
        stopLoading(view)
    }

    override fun updateLineChart(view: View, caseList: ArrayList<CaseData>) {
        val confirmedEntries = ArrayList<Entry>()
        val activeEntries = ArrayList<Entry>()
        val recoveredEntries = ArrayList<Entry>()
        val deathEntries = ArrayList<Entry>()
        for(index in 0 until caseList.size) {
            val case = caseList[index]
            val confirmed = case.confirmed.toFloat()
            val recovered = case.recovered.toFloat()
            val death = case.death.toFloat()
            val active = confirmed - recovered - death

            confirmedEntries.add(Entry(index.toFloat(), confirmed))
            activeEntries.add(Entry(index.toFloat(), active))
            recoveredEntries.add(Entry(index.toFloat(), recovered))
            deathEntries.add(Entry(index.toFloat(), death))
        }

        val confirmedDataSet = LineDataSet(confirmedEntries, view.context.getString(R.string.confirmed))
        val activeDataSet = LineDataSet(activeEntries, view.context.getString(R.string.active))
        val recoveredDataSet = LineDataSet(recoveredEntries, view.context.getString(R.string.recovered))
        val deathDataSet = LineDataSet(deathEntries, view.context.getString(R.string.death))
        confirmedDataSet.lineWidth = 1f
        confirmedDataSet.color = confirmedColor
        confirmedDataSet.circleRadius = 3f
        confirmedDataSet.setCircleColor(confirmedColor)
        activeDataSet.lineWidth = 1f
        activeDataSet.color = activeColor
        activeDataSet.circleRadius = 3f
        activeDataSet.setCircleColor(activeColor)
        recoveredDataSet.lineWidth = 1f
        recoveredDataSet.color = recoveredColor
        recoveredDataSet.circleRadius = 3f
        recoveredDataSet.setCircleColor(recoveredColor)
        deathDataSet.lineWidth = 1f
        deathDataSet.color = deathColor
        deathDataSet.circleRadius = 3f
        deathDataSet.setCircleColor(deathColor)

        val data = LineData(confirmedDataSet, activeDataSet, recoveredDataSet, deathDataSet)
        view.card_global_lineChart.data = data

        view.card_global_lineChart.invalidate()
        view.card_global_lineChart.zoom(3f, 1f, Float.MAX_VALUE, Float.MAX_VALUE)
        view.card_global_lineChart.animateX(1000)

        isLineFinished = true
        stopLoading(view)
    }

    override fun updateBarChart(view: View) {
        isBarFinished = true
        stopLoading(view)
    }

    override fun startLoading(view: View) {
        isCountFinished = false
        isPieFinished = false
        isLineFinished = false
        isBarFinished = false

        view.dashboard_loading.startAnimation(AnimationUtils.loadAnimation(view.context, R.anim.anim_updating))
        view.dashboard_loadingLayout.visibility = View.VISIBLE
    }

    override fun stopLoading(view: View) {
        if(isCountFinished && isPieFinished && isLineFinished && isBarFinished) {
            view.dashboard_loading.clearAnimation()
            view.dashboard_loadingLayout.visibility = View.GONE
        }
    }
}