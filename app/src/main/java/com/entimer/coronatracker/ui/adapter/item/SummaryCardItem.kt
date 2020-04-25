package com.entimer.coronatracker.ui.adapter.item

import com.entimer.coronatracker.data.CovidCaseData

data class SummaryCardItem(
    val title: String,
    val dataList: ArrayList<CovidCaseData>
)