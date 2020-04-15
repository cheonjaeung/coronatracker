package io.github.entimer.coronatracker.ui.search

import android.content.Context
import io.github.entimer.coronatracker.api.country.Country

class SearchPresenter(view: ISearchMVP.View): ISearchMVP.Presenter {
    private val view: ISearchMVP.View = view
    private val model = SearchModel(this)

    fun updateView(countries: ArrayList<Country>) {
        view.updateView(countries)
    }

    override fun getData(context: Context, keyword: String) {
        model.getData(context, keyword)
    }
}