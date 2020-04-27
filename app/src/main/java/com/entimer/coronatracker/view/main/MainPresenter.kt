package com.entimer.coronatracker.view.main

import android.content.Context
import com.entimer.coronatracker.view.main.summary.SummaryCardViewHolder

class MainPresenter(context: Context, view: MainContract.View): MainContract.Presenter {
    private val context = context
    private val view = view

    override fun getList() {
        view.setList(arrayListOf(
            MainCardListItem(MainCardListAdapter.SUMMARY, SummaryCardViewHolder.GLOBAL),
            MainCardListItem(MainCardListAdapter.SUMMARY, "South Korea"),
            MainCardListItem(MainCardListAdapter.SUMMARY, "USA")
        ))
    }
}