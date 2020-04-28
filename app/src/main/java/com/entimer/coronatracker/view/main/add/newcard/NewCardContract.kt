package com.entimer.coronatracker.view.main.add.newcard

import com.entimer.coronatracker.data.dataclass.CountryData

interface NewCardContract {
    interface View {
        var allCountries: ArrayList<CountryData>

        fun setList(data: ArrayList<CountryData>)
    }

    interface Presenter {
        fun getCountryList()
    }
}