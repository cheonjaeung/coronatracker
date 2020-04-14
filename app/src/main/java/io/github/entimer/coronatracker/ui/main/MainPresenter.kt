package io.github.entimer.coronatracker.ui.main

import android.content.Context
import io.github.entimer.coronatracker.ui.IMVP

class MainPresenter(view: IMVP.View): IMVP.Presenter {
    private val view: IMVP.View = view
    private val model = MainModel(this)

    fun initDatabase(context: Context) {
        model.initCountriesDatabase(context)
    }
}