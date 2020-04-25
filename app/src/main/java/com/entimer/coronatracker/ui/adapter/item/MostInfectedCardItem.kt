package com.entimer.coronatracker.ui.adapter.item

import com.entimer.coronatracker.data.CovidCaseData

data class MostInfectedCardItem(
    val title: String,
    val dataList: ArrayList<CovidCaseData>
)