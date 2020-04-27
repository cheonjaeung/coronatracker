package com.entimer.coronatracker.view.main

interface MainContract {
    interface View {
        fun setList(list: ArrayList<MainCardListItem>)
    }

    interface Presenter {
        fun getList()
    }
}