package com.entimer.coronatracker.view.main.add.newcard

import android.content.Context
import com.entimer.coronatracker.data.dataclass.CountryData

interface NewCardContract {
    interface View {
        fun setList(data: ArrayList<CountryData>)
    }

    interface Presenter {
        fun getCountryList(context: Context, keyword: String)
    }
}