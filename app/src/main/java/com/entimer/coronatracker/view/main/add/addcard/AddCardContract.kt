package com.entimer.coronatracker.view.main.add.addcard

import com.entimer.coronatracker.data.dataclass.CountryData

interface AddCardContract {
    interface View {
        var allCountries: ArrayList<CountryData>
    }

    interface Presenter {
        fun getCountryList()
    }
}