package com.entimer.coronatracker.view.main.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.entimer.coronatracker.R
import com.entimer.coronatracker.view.main.adapter.item.MainCardListItem
import com.github.mikephil.charting.charts.LineChart
import com.github.mikephil.charting.charts.PieChart
import java.lang.RuntimeException

class MainCardListAdapter(initData: ArrayList<MainCardListItem>): RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    private var items = initData

    override fun getItemViewType(position: Int): Int {
        return items[position].type
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val inflater = parent.context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        val view: View?

        return when(viewType) {
            MainCardListType.SUMMARY -> {
                view = inflater.inflate(R.layout.layout_summary_card, parent, false)
                SummaryCardViewHolder(view)
            }
            MainCardListType.MOST_INFECTED -> {
                view = inflater.inflate(R.layout.layout_most_infected_card, parent, false)
                MostInfectedCardViewHolder(view)
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
                }
            }
            MainCardListType.MOST_INFECTED -> {
                holder as MostInfectedCardViewHolder
                val cardItem = items[position].mostInfectedItem!!
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
        val detailButton = itemView.findViewById<ImageButton>(R.id.summaryCardDetailButton)!!
        val confirmed = itemView.findViewById<TextView>(R.id.summaryCardConfirmed)!!
        val actives = itemView.findViewById<TextView>(R.id.summaryCardActive)!!
        val recovered = itemView.findViewById<TextView>(R.id.summaryCardRecovered)!!
        val deaths = itemView.findViewById<TextView>(R.id.summaryCardDeaths)!!
        val pieChart = itemView.findViewById<PieChart>(R.id.summaryCardPieChart)!!
        val lineChart = itemView.findViewById<LineChart>(R.id.summaryCardLineChart)!!
        val updatedTime = itemView.findViewById<TextView>(R.id.summaryCardUpdatedTime)!!
        val chartButton = itemView.findViewById<ImageButton>(R.id.summaryCardChartButton)!!
        val mapButton = itemView.findViewById<ImageButton>(R.id.summaryCardMapButton)!!
    }

    inner class MostInfectedCardViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
        val title = itemView.findViewById<TextView>(R.id.mostInfectedCardTitle)!!
        val filterButton = itemView.findViewById<ImageButton>(R.id.mostInfectedCardFilterButton)!!
        val list = itemView.findViewById<RecyclerView>(R.id.mostInfectedCardList)!!
    }

    inner class AddCardViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
        val icon = itemView.findViewById<ImageView>(R.id.addCardIcon)!!
        val text = itemView.findViewById<TextView>(R.id.addCardText)!!
    }
}