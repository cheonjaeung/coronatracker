package com.entimer.coronatracker.view.main.add.newcard

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.entimer.coronatracker.R
import com.entimer.coronatracker.data.dataclass.CountryData

class NewCardListAdapter: RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    private var items = ArrayList<CountryData>()
    private lateinit var context: Context

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        context = parent.context
        val inflater = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        val view = inflater.inflate(R.layout.layout_country_list_item, parent, false)

        return CountryListViewHolder(view)
    }

    override fun getItemCount(): Int = items.size

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        holder as CountryListViewHolder
        holder.title.text = items[position].name
    }

    fun updateList(data: ArrayList<CountryData>) {
        items = data
        notifyDataSetChanged()
    }
}