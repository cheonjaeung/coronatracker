package io.github.entimer.coronatracker.ui.main

import io.github.entimer.coronatracker.ui.IMVP

class MainPresenter: IMVP.Presenter {
    private val view: IMVP.View
    private val model: MainModel

    constructor(view: IMVP.View) {
        this.view = view
        model = MainModel(this)
    }
}