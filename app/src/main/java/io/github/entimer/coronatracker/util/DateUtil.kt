package io.github.entimer.coronatracker.util

import java.text.SimpleDateFormat
import java.util.*

class DateUtil {
    fun getYear(date: String): String {
        return date.split("-")[0]
    }

    fun getMonth(date: String): String {
        return date.split("-")[1]
    }

    fun getDate(date: String): String {
        return date.split("-")[2]
    }

    fun getChangedDate(date: String, amount: Int): String {
        val splited = date.split("-")
        val year = splited[0].toInt()
        val month = splited[1].toInt()
        val date = splited[2].toInt()

        val calendar = Calendar.getInstance()
        calendar.set(Calendar.YEAR, year)
        calendar.set(Calendar.MONTH, month - 1)
        calendar.set(Calendar.DATE, date)

        calendar.add(Calendar.DATE, amount)

        val dateFormat = SimpleDateFormat("yyyy-MM-dd")
        return dateFormat.format(calendar.time)
    }

    fun dateToFloat(date: String): Float {
        val splited = date.split("-")
        val year = splited[0].toFloat()
        val month = splited[1].toFloat()
        val date = splited[2].toFloat()

        return date + (month * 100) + (year * 10000)
    }

    fun floatToDate(float: Float): String {
        var intDate = float.toInt()
        val year = intDate / 10000
        intDate -= year
        val month = intDate / 100
        intDate -= month
        val date = intDate
        return "$year-$month-$date"
    }
}