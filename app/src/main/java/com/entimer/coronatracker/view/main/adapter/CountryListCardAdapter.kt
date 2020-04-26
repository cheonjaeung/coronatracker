package com.entimer.coronatracker.view.main.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.entimer.coronatracker.R
import com.entimer.coronatracker.view.main.adapter.item.CountryItemCardItem

class CountryListCardAdapter(initData: ArrayList<CountryItemCardItem>): RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    private var items = initData
    private lateinit var context: Context

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        context = parent.context
        val inflater = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        val view = inflater.inflate(R.layout.layout_country_item_card, parent, false)
        return CountryItemCardViewHolder(view)
    }

    override fun getItemCount(): Int {
        return items.size
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        holder as CountryItemCardViewHolder
        holder.title.text = items[position].countryName
        holder.confirmed.text = items[position].confirmed.toString()
        holder.actives.text = items[position].actives.toString()
        holder.deaths.text = items[position].deaths.toString()
    }

    inner class CountryItemCardViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
        val title = itemView.findViewById<TextView>(R.id.countryItemCardTitle)!!
        val confirmed = itemView.findViewById<TextView>(R.id.countryItemCardConfirmed)!!
        val actives = itemView.findViewById<TextView>(R.id.countryItemCardActives)!!
        val recovered = itemView.findViewById<TextView>(R.id.countryItemCardRecovered)!!
        val deaths = itemView.findViewById<TextView>(R.id.countryItemCardDeaths)!!
    }
}