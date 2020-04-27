package com.entimer.coronatracker.view.main.summary

import android.content.Context
import android.view.View
import android.view.animation.AnimationUtils
import android.widget.ImageButton
import android.widget.TextView
import androidx.core.content.res.ResourcesCompat
import androidx.recyclerview.widget.RecyclerView
import com.entimer.coronatracker.R
import com.entimer.coronatracker.data.dataclass.CovidData
import com.github.mikephil.charting.charts.PieChart
import com.github.mikephil.charting.data.PieData
import com.github.mikephil.charting.data.PieDataSet
import com.github.mikephil.charting.data.PieEntry
import com.github.mikephil.charting.formatter.PercentFormatter

class SummaryCardViewHolder(context: Context, country: String, itemView: View): RecyclerView.ViewHolder(itemView), SummaryCardContract.View {
    companion object {
        const val GLOBAL = "Global"
    }

    private val context = context
    private val country = country
    private val presenter = SummaryCardPresenter(this)

    val title = itemView.findViewById<TextView>(R.id.summaryCardTitle)!!
    val refresh = itemView.findViewById<ImageButton>(R.id.summaryCardRefreshButton)!!
    val confirmed = itemView.findViewById<TextView>(R.id.summaryCardConfirmed)!!
    val actives = itemView.findViewById<TextView>(R.id.summaryCardActive)!!
    val recovered = itemView.findViewById<TextView>(R.id.summaryCardRecovered)!!
    val deaths = itemView.findViewById<TextView>(R.id.summaryCardDeaths)!!
    val pieChart = itemView.findViewById<PieChart>(R.id.summaryCardPieChart)!!
    val updatedTime = itemView.findViewById<TextView>(R.id.summaryCardUpdatedTime)!!

    init {
        refresh.setOnClickListener {
            startUpdateView()
            val animation = AnimationUtils.loadAnimation(context, R.anim.animation_refresh)
            refresh.startAnimation(animation)
        }

        pieChart.description.isEnabled = false
        pieChart.setExtraOffsets(5f, 5f, 5f, 5f)
        pieChart.setUsePercentValues(true)
        pieChart.isDrawHoleEnabled = false
        pieChart.transparentCircleRadius = 0f
        pieChart.setTouchEnabled(false)
        pieChart.legend.isEnabled = false
    }

    override fun startUpdateView() {
        if(country == GLOBAL) {
            presenter.getGlobalData()
            title.text = context.getString(R.string.mainCardListGlobalSummary)
        }
        else {
            presenter.getCountryData()
            title.text = country
        }
    }

    override fun updateView(data: CovidData) {
        confirmed.text = data.confirmed.toString()
        actives.text = data.actives.toString()
        recovered.text = data.recovered.toString()
        deaths.text = data.deaths.toString()

        updatedTime.text = data.time

        val entries = ArrayList<PieEntry>()
        entries.add(PieEntry(data.actives.toFloat()))
        entries.add(PieEntry(data.recovered.toFloat()))
        entries.add(PieEntry(data.deaths.toFloat()))

        val dataSet = PieDataSet(entries, "")
        dataSet.sliceSpace = 0f
        dataSet.colors = listOf(
            context.resources.getColor(R.color.colorActive),
            context.resources.getColor(R.color.colorRecovered),
            context.resources.getColor(R.color.colorDeath)
        )

        val data = PieData(dataSet)
        data.setValueFormatter(PercentFormatter(pieChart))
        data.setValueTextSize(16f)
        data.setValueTextColor(context.resources.getColor(R.color.colorText))
        val quicksand = ResourcesCompat.getFont(context, R.font.dongurami)
        data.setValueTypeface(quicksand)

        pieChart.data = data
        pieChart.invalidate()
        pieChart.animateY(1000)
    }
}