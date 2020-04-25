package com.entimer.coronatracker.view.main.adapter.item

data class MainCardListItem(
    val type: Int,
    val summaryItem: SummaryCardItem?,
    val mostInfectedItem: MostInfectedCardItem?,
    val addCardItem: AddCardItem?
)