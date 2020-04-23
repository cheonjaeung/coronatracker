package com.entimer.coronatracker.ui.main.global

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
import com.entimer.coronatracker.R
import com.entimer.coronatracker.util.DateValueFormatter
import com.entimer.coronatracker.api.covid.CaseData
import kotlinx.android.synthetic.main.fragment_global_inner.view.*
import kotlinx.android.synthetic.main.fragment_global.view.*
import java.text.DecimalFormat

class GlobalFragment: Fragment() {
    private lateinit var presenter: GlobalPresenter

    private var confirmedColor = 0
    private var activeColor = 0
    private var recoveredColor = 0
    private var deathColor = 0

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater!!.inflate(R.layout.fragment_global, container, false)

        presenter = GlobalPresenter(this, view)

        initViews(view)
        presenter.getData(view.context)

        return view
    }

    private fun initViews(view: View) {
        confirmedColor = view.context.resources.getColor(R.color.colorConfirmed)
        activeColor = view.context.resources.getColor(R.color.colorActive)
        recoveredColor = view.context.resources.getColor(R.color.colorRecovered)
        deathColor = view.context.resources.getColor(R.color.colorDeath)
        initPieChart(view)
        initLineChart(view)
    }

    private fun initPieChart(view: View) {
        val chart = view.global_pieChart
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
        val chart = view.global_lineChart
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

    fun updateCount(view: View, caseList: ArrayList<CaseData>) {
        val numFormat = DecimalFormat("###,###")
        val floatFormat = DecimalFormat("#.##")

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

        view.global_date.text = caseList[1].date

        view.global_confirmedNumber.text = numFormat.format(confirmed)
        view.global_activeNumber.text = numFormat.format(active)
        view.global_recoveredNumber.text = numFormat.format(recovered)
        view.global_deathNumber.text = numFormat.format(death)

        view.global_confirmedIncrease.text = "+${numFormat.format(confirmedIncrease)}"
        if(activeIncrease < 0) {
            view.global_activeIncrease.text = "${numFormat.format(activeIncrease)}"
        }
        else {
            view.global_activeIncrease.text = "+${numFormat.format(activeIncrease)}"
        }
        if(recoveredIncrease < 0) {
            view.global_recoveredIncrease.text = "${numFormat.format(recoveredIncrease)}"
        }
        else {
            view.global_recoveredIncrease.text = "+${numFormat.format(recoveredIncrease)}"
        }
        view.global_deathIncrease.text = "+${numFormat.format(deathIncrease)}"

        view.global_confirmedIncreaseRate.text = "+${floatFormat.format(confirmedRate)}%"
        if(activeIncrease < 0) {
            view.global_activeIncreaseRate.text = "${floatFormat.format(activeRate)}%"
        }
        else {
            view.global_activeIncreaseRate.text = "+${floatFormat.format(activeRate)}%"
        }
        if(recoveredIncrease < 0) {
            view.global_recoveredIncreaseRate.text = "${floatFormat.format(recoveredRate)}%"
        }
        else {
            view.global_recoveredIncreaseRate.text = "+${floatFormat.format(recoveredRate)}%"
        }
        view.global_deathIncreaseRate.text = "+${floatFormat.format(deathRate)}%"
    }

    fun updatePieChart(view: View, caseData: CaseData) {
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
        data.setValueFormatter(PercentFormatter(view.global_pieChart))
        data.setValueTextSize(16f)
        data.setValueTextColor(Color.WHITE)
        view.global_pieChart.data = data

        view.global_pieChart.invalidate()
        view.global_pieChart.animateY(1000)
    }

    fun updateLineChart(view: View, caseList: ArrayList<CaseData>) {
        val confirmedEntries = ArrayList<Entry>()
        val activeEntries = ArrayList<Entry>()
        val recoveredEntries = ArrayList<Entry>()
        val deathEntries = ArrayList<Entry>()
        for((index, case) in caseList.withIndex()) {
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
        view.global_lineChart.data = data

        view.global_lineChart.invalidate()
        view.global_lineChart.zoom(3f, 1f, Float.MAX_VALUE, Float.MAX_VALUE)
        view.global_lineChart.animateY(1000)
    }

    fun startLoading(view: View) {
        view.global_loading.startAnimation(AnimationUtils.loadAnimation(view.context, R.anim.anim_updating))
        view.global_loadingLayout.visibility = View.VISIBLE
    }

    fun stopLoading(view: View) {
        view.global_loading.clearAnimation()
        view.global_loadingLayout.visibility = View.GONE
    }
}