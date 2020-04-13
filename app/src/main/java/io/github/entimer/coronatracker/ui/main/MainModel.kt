package io.github.entimer.coronatracker.ui.main

class MainModel {
    private val presenter: MainPresenter

    constructor(presenter: MainPresenter) {
        this.presenter = presenter
    }
}