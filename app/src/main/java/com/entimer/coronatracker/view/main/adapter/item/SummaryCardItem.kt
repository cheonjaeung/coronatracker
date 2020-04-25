package com.entimer.coronatracker.view.main.adapter.item

import com.entimer.coronatracker.data.dataclass.CovidData

data class SummaryCardItem(
    val title: String,
    val recentData: CovidData?
)