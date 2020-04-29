package com.entimer.coronatracker.view.main

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.entimer.coronatracker.R
import com.entimer.coronatracker.view.main.add.AddCardViewHolder
import com.entimer.coronatracker.view.main.summary.SummaryCardViewHolder
import java.lang.RuntimeException

class MainCardListAdapter: RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    companion object {
        const val ADD = 0
        const val SUMMARY = 1
    }

    private var items = ArrayList<MainCardListItem>()
    private lateinit var context: Context

    override fun getItemViewType(position: Int): Int = items[position].viewType

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        context = parent.context
        val inflater = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        val view: View?

        return when(viewType) {
            ADD -> {
                view = inflater.inflate(R.layout.layout_add_card, parent, false)
                AddCardViewHolder(context, view)
            }
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

                holder.refresh.setOnClickListener {
                    holder.startUpdateView(items[position].option)
                    val animation = AnimationUtils.loadAnimation(context, R.anim.animation_refresh)
                    holder.refresh.startAnimation(animation)
                }

                holder.remove.setOnClickListener {
                    holder.presenter.removeCard(context, items[position].option)
                    items.remove(MainCardListItem(items[position].viewType, items[position].option))
                    notifyDataSetChanged()
                    Toast.makeText(context, context.getString(R.string.mainCardRemovedMessage), Toast.LENGTH_LONG).show()
                }

                if(items[position].option == SummaryCardViewHolder.GLOBAL)
                    holder.remove.visibility = View.GONE
                else
                    holder.remove.visibility = View.VISIBLE
            }
        }
    }

    fun updateList(data: ArrayList<MainCardListItem>) {
        items = data
        notifyDataSetChanged()
    }
}