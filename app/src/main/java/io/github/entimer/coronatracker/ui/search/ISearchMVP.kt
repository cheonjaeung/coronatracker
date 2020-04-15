package io.github.entimer.coronatracker.ui.search

import android.content.Context
import io.github.entimer.coronatracker.api.country.Country

interface ISearchMVP {
    interface View {
        fun updateView(countries: ArrayList<Country>)
    }

    interface Presenter {
        fun getData(context: Context, keyword: String)
    }
}