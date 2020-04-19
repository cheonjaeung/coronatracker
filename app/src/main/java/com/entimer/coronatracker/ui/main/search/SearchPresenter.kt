package com.entimer.coronatracker.ui.main.search

import android.content.Context
import android.view.View
import com.entimer.coronatracker.ui.base.IMvp

class SearchPresenter(view: IMvp.View.Search, fragmentView: View): IMvp.Presenter.Search {
    private val view = view
    private val fragmentView = fragmentView
    private val model = SearchModel(this)

    override fun getData(context: Context) {

    }
}