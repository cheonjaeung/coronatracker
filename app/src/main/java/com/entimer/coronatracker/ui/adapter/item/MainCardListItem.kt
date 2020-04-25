package com.entimer.coronatracker.ui.adapter.item

data class MainCardListItem(
    val type: Int,
    val summaryItem: SummaryCardItem?,
    val mostInfectedItem: MostInfectedCardItem?,
    val addCardItem: AddCardItem?
)