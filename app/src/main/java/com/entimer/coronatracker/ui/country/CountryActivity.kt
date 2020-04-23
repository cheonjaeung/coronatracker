package com.entimer.coronatracker.ui.country

import android.graphics.Color
import android.os.Bundle
import android.view.View
import android.view.animation.AnimationUtils
import androidx.appcompat.app.AppCompatActivity
import com.entimer.coronatracker.R
import com.entimer.coronatracker.api.covid.CaseData
import com.entimer.coronatracker.util.DateValueFormatter
import com.github.mikephil.charting.components.Legend
import com.github.mikephil.charting.components.XAxis
import com.github.mikephil.charting.data.*
import com.github.mikephil.charting.formatter.LargeValueFormatter
import com.github.mikephil.charting.formatter.PercentFormatter
import kotlinx.android.synthetic.main.activity_country.*
import kotlinx.android.synthetic.main.activity_country_inner.*
import java.text.DecimalFormat

class CountryActivity: AppCompatActivity() {
    private lateinit var presenter: CountryPresenter
    private var name = ""
    private var code = ""

    private var confirmedColor = 0
    private var activeColor = 0
    private var recoveredColor = 0
    private var deathColor = 0
    private var textColor = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_country)

        presenter = CountryPresenter(this)

        initViews()
        initListeners()

        presenter.getData(applicationContext, code.toLowerCase())
    }

    private fun initViews() {
        val intent = intent
        name = intent.getStringExtra("name")
        code = intent.getStringExtra("code")
        country_toolbar.title = name

        confirmedColor = resources.getColor(R.color.colorConfirmed)
        activeColor = resources.getColor(R.color.colorActive)
        recoveredColor = resources.getColor(R.color.colorRecovered)
        deathColor = resources.getColor(R.color.colorDeath)
        textColor = resources.getColor(R.color.colorText)

        initPieChart()
        initLineChart()
    }

    private fun initPieChart() {
        val chart = country_pieChart
        chart.setUsePercentValues(true)
        chart.description.isEnabled = false
        chart.dragDecelerationFrictionCoef = 0.5f
        chart.holeRadius = 55f
        chart.transparentCircleRadius = 60f
        chart.rotationAngle = 0f
        chart.isRotationEnabled = true
        chart.isHighlightPerTapEnabled = true
        chart.centerText = getString(R.string.globalPieChartLabel)
        chart.setCenterTextColor(textColor)
        chart.setHoleColor(resources.getColor(R.color.colorBackground))
        chart.setTransparentCircleColor(resources.getColor(R.color.colorBackground))

        val legend = chart.legend
        legend.horizontalAlignment = Legend.LegendHorizontalAlignment.CENTER
        legend.verticalAlignment = Legend.LegendVerticalAlignment.TOP
        legend.orientation = Legend.LegendOrientation.HORIZONTAL
        legend.xEntrySpace = 12f
        legend.textColor = textColor
    }

    private fun initLineChart() {
        val chart = country_lineChart
        chart.setExtraOffsets(0f, 10f, 0f, 0f)
        chart.description.isEnabled = false
        chart.setTouchEnabled(true)
        chart.isDragEnabled = true
        chart.isScaleXEnabled = false
        chart.isScaleYEnabled = false
        chart.isDoubleTapToZoomEnabled = false

        val legend = chart.legend
        legend.horizontalAlignment = Legend.LegendHorizontalAlignment.CENTER
        legend.verticalAlignment = Legend.LegendVerticalAlignment.TOP
        legend.orientation = Legend.LegendOrientation.HORIZONTAL
        legend.xEntrySpace = 12f
        legend.textColor = textColor

        val axisX = chart.xAxis
        axisX.position = XAxis.XAxisPosition.BOTTOM
        axisX.textColor = textColor
        axisX.valueFormatter = DateValueFormatter(applicationContext)

        val axisLeft = chart.axisLeft
        axisLeft.axisMinimum = 0f
        axisLeft.textColor = textColor
        axisLeft.valueFormatter = LargeValueFormatter()

        val axisRight = chart.axisRight
        axisRight.isEnabled = false
    }

    private fun initListeners() {
        country_toolbar.setNavigationOnClickListener {
            finish()
        }
    }

    fun updateCount(caseList: ArrayList<CaseData>) {
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

        country_date.text = caseList[1].date

        country_confirmedNumber.text = numFormat.format(confirmed)
        country_activeNumber.text = numFormat.format(active)
        country_recoveredNumber.text = numFormat.format(recovered)
        country_deathNumber.text = numFormat.format(death)

        country_confirmedIncrease.text = "+${numFormat.format(confirmedIncrease)}"
        if(activeIncrease < 0) {
            country_activeIncrease.text = "${numFormat.format(activeIncrease)}"
        }
        else {
            country_activeIncrease.text = "+${numFormat.format(activeIncrease)}"
        }
        if(recoveredIncrease < 0) {
            country_recoveredIncrease.text = "${numFormat.format(recoveredIncrease)}"
        }
        else {
            country_recoveredIncrease.text = "+${numFormat.format(recoveredIncrease)}"
        }
        country_deathIncrease.text = "+${numFormat.format(deathIncrease)}"

        country_confirmedIncreaseRate.text = "+${floatFormat.format(confirmedRate)}%"
        if(activeIncrease < 0) {
            country_activeIncreaseRate.text = "${floatFormat.format(activeRate)}%"
        }
        else {
            country_activeIncreaseRate.text = "+${floatFormat.format(activeRate)}%"
        }
        if(recoveredIncrease < 0) {
            country_recoveredIncreaseRate.text = "${floatFormat.format(recoveredRate)}%"
        }
        else {
            country_recoveredIncreaseRate.text = "+${floatFormat.format(recoveredRate)}%"
        }
        country_deathIncreaseRate.text = "+${floatFormat.format(deathRate)}%"
    }

    fun updatePieChart(caseData: CaseData) {
        val confirmed = caseData.confirmed
        val recovered = caseData.recovered
        val death = caseData.death
        val active = confirmed - recovered - death

        val entries = ArrayList<PieEntry>()
        entries.add(PieEntry(active.toFloat(), getString(R.string.active)))
        entries.add(PieEntry(recovered.toFloat(), getString(R.string.recovered)))
        entries.add(PieEntry(death.toFloat(), getString(R.string.death)))

        val dataSet = PieDataSet(entries, "")
        dataSet.sliceSpace = 3f
        dataSet.selectionShift = 6f
        val colors = ArrayList<Int>()
        colors.add(activeColor)
        colors.add(recoveredColor)
        colors.add(deathColor)
        dataSet.colors = colors

        val data = PieData(dataSet)
        data.setValueFormatter(PercentFormatter(country_pieChart))
        data.setValueTextSize(16f)
        data.setValueTextColor(Color.WHITE)
        country_pieChart.data = data

        country_pieChart.invalidate()
        country_pieChart.animateY(1000)
    }

    fun updateLineChart(caseList: ArrayList<CaseData>) {
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

        val confirmedDataSet = LineDataSet(confirmedEntries, getString(R.string.confirmed))
        val activeDataSet = LineDataSet(activeEntries, getString(R.string.active))
        val recoveredDataSet = LineDataSet(recoveredEntries, getString(R.string.recovered))
        val deathDataSet = LineDataSet(deathEntries, getString(R.string.death))
        confirmedDataSet.lineWidth = 1f
        confirmedDataSet.color = confirmedColor
        confirmedDataSet.setDrawCircles(false)
        activeDataSet.lineWidth = 1f
        activeDataSet.color = activeColor
        activeDataSet.setDrawCircles(false)
        recoveredDataSet.lineWidth = 1f
        recoveredDataSet.color = recoveredColor
        recoveredDataSet.setDrawCircles(false)
        deathDataSet.lineWidth = 1f
        deathDataSet.color = deathColor
        deathDataSet.setDrawCircles(false)

        val data = LineData(confirmedDataSet, activeDataSet, recoveredDataSet, deathDataSet)
        country_lineChart.data = data

        country_lineChart.invalidate()
        country_lineChart.animateY(1000)
    }

    fun startLoading() {
        country_loading.startAnimation(AnimationUtils.loadAnimation(applicationContext, R.anim.anim_updating))
        country_loadingLayout.visibility = View.VISIBLE
    }

    fun stopLoading() {
        country_loading.clearAnimation()
        country_loadingLayout.visibility = View.GONE
    }
}