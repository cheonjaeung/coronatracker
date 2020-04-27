package com.entimer.coronatracker.view.main

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.entimer.coronatracker.R
import com.entimer.coronatracker.view.main.summary.SummaryCardViewHolder
import java.lang.RuntimeException

class MainCardListAdapter: RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    companion object {
        const val GLOBAL_SUMMARY = 0
        const val COUNTRY_SUMMARY = 1
    }

    private var items = ArrayList<Int>()
    private lateinit var context: Context

    override fun getItemViewType(position: Int): Int {
        return items[position]
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        context = parent.context
        val inflater = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        val view: View?

        return when(viewType) {
            GLOBAL_SUMMARY -> {
                view = inflater.inflate(R.layout.layout_summary_card, parent, false)
                SummaryCardViewHolder(context, SummaryCardViewHolder.GLOBAL, view)
            }
            COUNTRY_SUMMARY -> {
                view = inflater.inflate(R.layout.layout_summary_card, parent, false)
                SummaryCardViewHolder(context, "South Korea", view)
            }
            else -> {
                throw RuntimeException("Incorrect view type!")
            }
        }
    }

    override fun getItemCount(): Int = items.size

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when(items[position]) {
            GLOBAL_SUMMARY, COUNTRY_SUMMARY -> {
                holder as SummaryCardViewHolder
                holder.startUpdateView()
            }
        }
    }

    fun updateList(data: ArrayList<Int>) {
        items = data
        notifyDataSetChanged()
    }
}