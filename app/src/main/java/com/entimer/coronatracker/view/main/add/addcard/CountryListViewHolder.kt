package com.entimer.coronatracker.view.main.add.addcard

import android.view.View
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.entimer.coronatracker.R

class CountryListViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
    val title = itemView.findViewById<TextView>(R.id.countryListItemTitle)
}