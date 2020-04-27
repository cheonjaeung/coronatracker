package com.entimer.coronatracker.view.main

interface MainContract {
    interface View {
        fun setList(list: ArrayList<Int>)
    }

    interface Presenter {
        fun getList()
    }
}