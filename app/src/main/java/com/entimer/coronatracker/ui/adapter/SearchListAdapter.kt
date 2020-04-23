package com.entimer.coronatracker.ui.adapter

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.entimer.coronatracker.R
import com.entimer.coronatracker.api.iso3166.Iso3166Data
import com.entimer.coronatracker.ui.country.CountryActivity

class SearchListAdapter: RecyclerView.Adapter<SearchListAdapter.ViewHolder>() {
    private var items = ArrayList<Iso3166Data>()

    class ViewHolder(view: View, context: Context): RecyclerView.ViewHolder(view) {
        val nameView: TextView = view.findViewById(R.id.item_search_resultList_countryName)
        val codeView: TextView = view.findViewById(R.id.item_search_resultList_countryCode)

        init {
            view.setOnClickListener {
                val intent = Intent(context, CountryActivity::class.java)
                intent.putExtra("name", nameView.text)
                intent.putExtra("code", codeView.text)
                context.startActivity(intent)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val context = parent.context
        val inflater = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        val view = inflater.inflate(R.layout.item_search_list, parent, false)
        return ViewHolder(view, context)
    }

    override fun getItemCount(): Int {
        return items.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.nameView.text = items[position].name
        holder.codeView.text = items[position].alpha3
    }

    fun updateList(items: ArrayList<Iso3166Data>) {
        this.items = items
        notifyDataSetChanged()
    }
}