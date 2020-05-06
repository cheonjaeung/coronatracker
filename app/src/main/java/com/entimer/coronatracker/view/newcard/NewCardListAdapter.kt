package com.entimer.coronatracker.view.newcard

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.entimer.coronatracker.R
import com.entimer.coronatracker.data.dataclass.CountryData
import com.entimer.coronatracker.util.SharedPreferencesUtil
import com.entimer.coronatracker.view.main.MainCardListAdapter
import com.entimer.coronatracker.view.main.MainCardListItem

class NewCardListAdapter: RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    private var items = ArrayList<CountryData>()
    private lateinit var context: Context

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        context = parent.context
        val inflater = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        val view = inflater.inflate(R.layout.layout_country_list_item, parent, false)

        return NewCardListViewHolder(view)
    }

    override fun getItemCount(): Int = items.size

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        holder as NewCardListViewHolder

        holder.run {
            title.text = items[position].name

            button.setOnClickListener {
                val sf = SharedPreferencesUtil(context)
                val cards = sf.getCard()
                cards.add(MainCardListItem(MainCardListAdapter.SUMMARY, items[position].name))
                sf.setCard(cards)

                Toast.makeText(context, context.getString(R.string.newCardAddedMessage), Toast.LENGTH_LONG).show()
            }
        }
    }

    fun updateList(data: ArrayList<CountryData>) {
        items = data
        notifyDataSetChanged()
    }

    inner class NewCardListViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
        val title = itemView.findViewById<TextView>(R.id.countryListItemTitle)
        val button = itemView.findViewById<ImageButton>(R.id.countryListItemAddButton)
    }
}