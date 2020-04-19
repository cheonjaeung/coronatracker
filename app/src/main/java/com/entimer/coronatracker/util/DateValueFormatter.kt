package com.entimer.coronatracker.util

import android.content.Context
import com.github.mikephil.charting.components.AxisBase
import com.github.mikephil.charting.formatter.ValueFormatter
import com.entimer.coronatracker.R

class DateValueFormatter(context: Context): ValueFormatter() {
    private val apiStartDate = context.getString(R.string.apiStartDate)
    private val monthString = context.resources.getStringArray(R.array.month)

    override fun getAxisLabel(value: Float, axis: AxisBase?): String {
        val dateUtil = DateUtil()

        val date = dateUtil.getChangedDate(apiStartDate, value.toInt())
        val month = dateUtil.getMonth(date)
        val dateOfMonth = dateUtil.getDate(date)

        return "${monthString[month.toInt() - 1]} $dateOfMonth"
    }
}