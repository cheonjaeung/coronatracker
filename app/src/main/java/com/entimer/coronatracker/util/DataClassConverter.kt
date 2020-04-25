package com.entimer.coronatracker.util

import com.entimer.coronatracker.data.dataclass.ApiRecentData
import com.entimer.coronatracker.data.room.RecentEntity

fun apiRecentData2RecentEntry(data: ApiRecentData): RecentEntity {
    val time = data.lastUpdate
    val confirmed = data.confirmed.value
    val recovered = data.recovered.value
    val deaths = data.deaths.value
    val actives = confirmed - recovered - deaths
    return RecentEntity(0, time, "recent", confirmed, actives, recovered, deaths)
}