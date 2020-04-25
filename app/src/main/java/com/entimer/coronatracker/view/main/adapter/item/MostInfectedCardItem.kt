package com.entimer.coronatracker.view.main.adapter.item

import com.entimer.coronatracker.data.dataclass.CovidCaseData

data class MostInfectedCardItem(
    val title: String,
    val dataList: ArrayList<CovidCaseData>
)