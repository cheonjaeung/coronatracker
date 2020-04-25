package com.entimer.coronatracker.ui.main_new

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.entimer.coronatracker.R
import com.entimer.coronatracker.ui.adapter.MainCardListAdapter
import com.entimer.coronatracker.ui.adapter.item.*

class MainNewActivity: AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main_new)

        initViews()
    }

    private fun initViews() {
        val adapter = MainCardListAdapter(initMainCardListItems())
        val list = findViewById<RecyclerView>(R.id.mainCardList)
        list.adapter = adapter
        list.layoutManager = LinearLayoutManager(applicationContext)
    }

    private fun initMainCardListItems(): ArrayList<MainCardListItem> {
        val list = ArrayList<MainCardListItem>()

        list.add(
            MainCardListItem(
                MainCardListType.SUMMARY,
                SummaryCardItem(
                    getString(R.string.mainCardListGlobalSummary),
                    arrayListOf()
                ),
                null,
                null
            )
        )

        list.add(
            MainCardListItem(
                MainCardListType.MOST_INFECTED,
                null,
                MostInfectedCardItem(
                    getString(R.string.mainCardListMostInfected),
                    arrayListOf()
                ),
                null
            )
        )

        list.add(
            MainCardListItem(
                MainCardListType.ADD,
                null,
                null,
                AddCardItem(
                    R.drawable.icon_add,
                    getString(R.string.mainCardAdd)
                )
            )
        )

        return list
    }
}