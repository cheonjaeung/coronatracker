package com.entimer.coronatracker.view.main.add

import android.content.Context
import android.content.Intent
import android.view.View
import android.widget.LinearLayout
import androidx.recyclerview.widget.RecyclerView
import com.entimer.coronatracker.R
import com.entimer.coronatracker.view.main.add.addcard.AddCardActivity

class AddCardViewHolder(context: Context, itemView: View): RecyclerView.ViewHolder(itemView) {
    private val context = context

    private val cardLayout = itemView.findViewById<LinearLayout>(R.id.addCardLayout)

    init {
        cardLayout.setOnClickListener {
            val intent = Intent(context, AddCardActivity::class.java)
            context.startActivity(intent)
        }
    }
}