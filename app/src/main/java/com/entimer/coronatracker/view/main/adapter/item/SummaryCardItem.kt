package com.entimer.coronatracker.view.main.adapter.item

import com.entimer.coronatracker.data.dataclass.CovidCaseData

data class SummaryCardItem(
    val title: String,
    val dataList: ArrayList<CovidCaseData>
)