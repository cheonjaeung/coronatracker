package com.entimer.coronatracker.ui.main.search

import android.content.Context
import android.view.View
import com.entimer.coronatracker.api.iso3166.Iso3166Data

class SearchPresenter(view: SearchFragment, fragmentView: View) {
    private val view = view
    private val fragmentView = fragmentView
    private val model = SearchModel(this)

    fun getData(context: Context, keyword: String) {
        model.getCountries(context, keyword)
    }

    fun updateSearchList(list: ArrayList<Iso3166Data>) {
        view.updateSearchList(fragmentView, list)
    }
}