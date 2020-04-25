package com.entimer.coronatracker.view.main

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.entimer.coronatracker.R
import com.entimer.coronatracker.data.dataclass.CovidData
import com.entimer.coronatracker.ui.setting.SettingActivity
import com.entimer.coronatracker.view.main.adapter.MainCardListAdapter
import com.entimer.coronatracker.view.main.adapter.MainCardListType
import com.entimer.coronatracker.view.main.adapter.item.AddCardItem
import com.entimer.coronatracker.view.main.adapter.item.MainCardListItem
import com.entimer.coronatracker.view.main.adapter.item.MostInfectedCardItem
import com.entimer.coronatracker.view.main.adapter.item.SummaryCardItem
import kotlinx.android.synthetic.main.activity_main_new.*

class MainActivity: AppCompatActivity(), MainContract.View {
    private lateinit var presenter: MainPresenter

    private lateinit var adapter: MainCardListAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main_new)

        presenter = MainPresenter(applicationContext, this)
        presenter.getRecentData()

        initViews()
    }

    private fun initViews() {
        adapter = MainCardListAdapter(makeMainCardListItems(null))
        val list = findViewById<RecyclerView>(R.id.mainCardList)
        list.adapter = adapter
        list.layoutManager = LinearLayoutManager(applicationContext)

        mainToolbar.setOnMenuItemClickListener { item ->
            if(item.itemId == R.id.setting) {
                val intent = Intent(applicationContext, SettingActivity::class.java)
                startActivity(intent)
                true
            }
            false
        }
    }

    private fun makeMainCardListItems(recentData: CovidData?): ArrayList<MainCardListItem> {
        val list = ArrayList<MainCardListItem>()

        list.add(
            MainCardListItem(
                MainCardListType.SUMMARY,
                SummaryCardItem(
                    getString(R.string.mainCardListGlobalSummary),
                    recentData
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

    override fun onBackPressed() {
        finishAffinity()
    }

    override fun setRecentData(data: CovidData?) {
        adapter.updateList(makeMainCardListItems(data))
    }
}