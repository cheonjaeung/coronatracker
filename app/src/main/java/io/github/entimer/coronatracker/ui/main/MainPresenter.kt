package io.github.entimer.coronatracker.ui.main

import android.content.Context
import io.github.entimer.coronatracker.ui.IMVP

class MainPresenter(view: IMVP.View): IMVP.Presenter {
    private val view: IMVP.View = view
    private val model: MainModel = MainModel(this)

    fun saveCountriesInDatabase(context: Context) {
        model.getCountriesFromWeb(context)
    }
}