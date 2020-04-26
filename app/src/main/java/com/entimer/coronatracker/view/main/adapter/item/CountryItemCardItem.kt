package com.entimer.coronatracker.view.main.adapter.item

data class CountryItemCardItem(
    val countryName: String,
    val confirmed: Int,
    val actives: Int,
    val recovered: Int,
    val deaths: Int
)