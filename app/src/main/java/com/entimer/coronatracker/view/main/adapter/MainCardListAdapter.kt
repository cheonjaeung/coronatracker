package com.entimer.coronatracker.view.main.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.core.content.res.ResourcesCompat
import androidx.recyclerview.widget.RecyclerView
import com.entimer.coronatracker.R
import com.entimer.coronatracker.view.main.adapter.item.MainCardListItem
import com.github.mikephil.charting.charts.PieChart
import com.github.mikephil.charting.data.PieData
import com.github.mikephil.charting.data.PieDataSet
import com.github.mikephil.charting.data.PieEntry
import com.github.mikephil.charting.formatter.PercentFormatter
import java.lang.RuntimeException

class MainCardListAdapter(initData: ArrayList<MainCardListItem>): RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    private var items = initData
    private lateinit var context: Context

    override fun getItemViewType(position: Int): Int {
        return items[position].type
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        context = parent.context
        val inflater = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        val view: View?

        return when(viewType) {
            MainCardListType.SUMMARY -> {
                view = inflater.inflate(R.layout.layout_summary_card, parent, false)
                SummaryCardViewHolder(view)
            }
            MainCardListType.COUNTRY_LIST -> {
                view = inflater.inflate(R.layout.layout_country_list_card, parent, false)
                CountryListCardViewHolder(view)
            }
            MainCardListType.ADD -> {
                view = inflater.inflate(R.layout.layout_add_card, parent, false)
                AddCardViewHolder(view)
            }
            else -> {
                throw RuntimeException("Incorrect view type!")
            }
        }
    }

    override fun getItemCount(): Int {
        return items.size
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when(items[position].type) {
            MainCardListType.SUMMARY -> {
                holder as SummaryCardViewHolder
                val cardItem = items[position].summaryItem!!
                holder.title.text = cardItem.title

                if(cardItem.recentData == null) {
                    holder.confirmed.text = "?"
                    holder.actives.text = "?"
                    holder.recovered.text = "?"
                    holder.deaths.text = "?"
                    holder.updatedTime.text = "Updating..."
                }
                else {
                    holder.confirmed.text = cardItem.recentData.confirmed.toString()
                    holder.actives.text = cardItem.recentData.actives.toString()
                    holder.recovered.text = cardItem.recentData.recovered.toString()
                    holder.deaths.text = cardItem.recentData.deaths.toString()
                    holder.updatedTime.text = cardItem.recentData.time

                    val entries = ArrayList<PieEntry>()
                    entries.add(PieEntry(cardItem.recentData.actives.toFloat()))
                    entries.add(PieEntry(cardItem.recentData.recovered.toFloat()))
                    entries.add(PieEntry(cardItem.recentData.deaths.toFloat()))

                    val dataSet = PieDataSet(entries, "")
                    dataSet.sliceSpace = 0f
                    dataSet.colors = listOf(
                        context.resources.getColor(R.color.colorActive),
                        context.resources.getColor(R.color.colorRecovered),
                        context.resources.getColor(R.color.colorDeath)
                    )

                    val data = PieData(dataSet)
                    data.setValueFormatter(PercentFormatter(holder.pieChart))
                    data.setValueTextSize(16f)
                    data.setValueTextColor(context.resources.getColor(R.color.colorText))
                    val quicksand = ResourcesCompat.getFont(context, R.font.dongurami)
                    data.setValueTypeface(quicksand)

                    holder.pieChart.data = data
                    holder.pieChart.invalidate()
                    holder.pieChart.animateY(1000)
                }
            }
            MainCardListType.COUNTRY_LIST -> {
                holder as CountryListCardViewHolder
                val cardItem = items[position].countryListItem!!
                holder.title.text = cardItem.title
            }
            MainCardListType.ADD -> {
                holder as AddCardViewHolder
                val cardItem = items[position].addCardItem!!
                holder.icon.setImageResource(cardItem.icon)
                holder.text.text = cardItem.text
            }
        }
    }

    fun updateList(data: ArrayList<MainCardListItem>) {
        items = data
        notifyDataSetChanged()
    }

    inner class SummaryCardViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
        val title = itemView.findViewById<TextView>(R.id.summaryCardTitle)!!
        val confirmed = itemView.findViewById<TextView>(R.id.summaryCardConfirmed)!!
        val actives = itemView.findViewById<TextView>(R.id.summaryCardActive)!!
        val recovered = itemView.findViewById<TextView>(R.id.summaryCardRecovered)!!
        val deaths = itemView.findViewById<TextView>(R.id.summaryCardDeaths)!!
        val pieChart = itemView.findViewById<PieChart>(R.id.summaryCardPieChart)!!
        val updatedTime = itemView.findViewById<TextView>(R.id.summaryCardUpdatedTime)!!

        init {
            pieChart.description.isEnabled = false
            pieChart.setExtraOffsets(5f, 5f, 5f, 5f)
            pieChart.setUsePercentValues(true)
            pieChart.isDrawHoleEnabled = false
            pieChart.transparentCircleRadius = 0f
            pieChart.setTouchEnabled(false)
            pieChart.legend.isEnabled = false
        }
    }

    inner class CountryListCardViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
        val title = itemView.findViewById<TextView>(R.id.countryListCardTitle)!!
        val list = itemView.findViewById<RecyclerView>(R.id.countryListCardList)!!
    }

    inner class AddCardViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
        val icon = itemView.findViewById<ImageView>(R.id.addCardIcon)!!
        val text = itemView.findViewById<TextView>(R.id.addCardText)!!
    }
}