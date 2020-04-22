package com.entimer.coronatracker.ui.main.search

import android.content.Context
import android.view.View
import com.entimer.coronatracker.api.iso3166.Iso3166Data

class SearchPresenter(view: SearchFragment, fragmentView: View) {
    private val view = view
    private val fragmentView = fragmentView
    private val model = SearchModel(this)

    fun initData(context: Context) {
        view.startInitLoading(fragmentView)
        if(model.checkUpdated(context)) {
            onInitDataFinished()
        }
        else {
            model.getDataFromApi(context)
        }
    }

    fun onInitDataFinished() {
        view.stopInitLoading(fragmentView)
    }

    fun getData(context: Context, keyword: String) {
        view.startLoading(fragmentView)
        model.getDataFromDatabase(context, keyword)
    }

    fun onGetDataFinished(dataList: ArrayList<Iso3166Data>) {
        view.updateSearchList(dataList)
        view.stopLoading(fragmentView)
    }
}