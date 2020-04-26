package com.entimer.coronatracker.view.main.adapter.item

data class MainCardListItem(
    val type: Int,
    val summaryItem: SummaryCardItem?,
    val countryListItem: CountryListCardItem?,
    val addCardItem: AddCardItem?
)