package io.github.entimer.coronatracker.ui.main

import android.content.Context

class MainPresenter(view: IMainMVP.View): IMainMVP.Presenter {
    private val view: IMainMVP.View = view
    private val model = MainModel(this)

    override fun getData(context: Context) {
        model.initCountriesDatabase(context)
    }
}