package com.entimer.coronatracker.view.main

import android.content.Context

class MainPresenter(context: Context, view: MainContract.View): MainContract.Presenter {
    private val context = context
    private val view = view

    override fun getList() {
        view.setList(arrayListOf(
            MainCardListAdapter.GLOBAL_SUMMARY,
            MainCardListAdapter.COUNTRY_SUMMARY
        ))
    }
}