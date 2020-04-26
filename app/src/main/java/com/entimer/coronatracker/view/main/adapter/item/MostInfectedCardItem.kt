package com.entimer.coronatracker.view.main.adapter.item

import com.entimer.coronatracker.data.dataclass.CovidData

data class MostInfectedCardItem(
    val title: String,
    val dataList: ArrayList<CovidData>
)