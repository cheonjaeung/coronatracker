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
        const val SUMMARY = 0
    }

    private var items = ArrayList<MainCardListItem>()
    private lateinit var context: Context

    override fun getItemViewType(position: Int): Int = items[position].viewType

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        context = parent.context
        val inflater = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        val view: View?

        return when(viewType) {
            SUMMARY -> {
                view = inflater.inflate(R.layout.layout_summary_card, parent, false)
                SummaryCardViewHolder(context, view)
            }
            else -> {
                throw RuntimeException("Incorrect view type!")
            }
        }
    }

    override fun getItemCount(): Int = items.size

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when(items[position].viewType) {
            SUMMARY -> {
                holder as SummaryCardViewHolder
                holder.startUpdateView(items[position].option)
            }
        }
    }

    fun updateList(data: ArrayList<MainCardListItem>) {
        items = data
        notifyDataSetChanged()
    }
}