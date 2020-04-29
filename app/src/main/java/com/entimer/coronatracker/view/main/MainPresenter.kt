package com.entimer.coronatracker.view.main

import android.content.Context
import com.entimer.coronatracker.util.SharedPreferencesUtil
import com.entimer.coronatracker.view.main.summary.SummaryCardViewHolder

class MainPresenter(context: Context, view: MainContract.View): MainContract.Presenter {
    private val context = context
    private val view = view

    override fun getList() {
        val list = ArrayList<MainCardListItem>()
        list.add(getGlobalSummaryCard())

        val sf = SharedPreferencesUtil(context)
        val cardList = sf.getCard()
        list.addAll(cardList)

        list.add(getAddCard())
        view.setList(list)
    }

    private fun getGlobalSummaryCard(): MainCardListItem = MainCardListItem(MainCardListAdapter.SUMMARY, SummaryCardViewHolder.GLOBAL)

    private fun getAddCard(): MainCardListItem = MainCardListItem(MainCardListAdapter.ADD, "add")
}