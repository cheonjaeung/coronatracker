package io.github.entimer.coronatracker.sharedpreference

import android.content.Context

class SharedPreferenceUtil(context: Context) {
    companion object {
        private const val FILE_NAME = "coronatracker"
        private const val KEY_LATEST_DATE = "latestDate"
        private const val KEY_FIRST_DATE = "firstDate"
    }

    private val sharedPref = context.getSharedPreferences(FILE_NAME, Context.MODE_PRIVATE)
    private val editor = sharedPref.edit()

    init {
        setFirstDate()
    }

    fun setLatestDate(date: String) {
        editor.putString(KEY_LATEST_DATE, date)
        editor.commit()
    }

    fun getLatestDate(): String {
        return sharedPref.getString(KEY_LATEST_DATE, "2020-01-22")!!
    }

    private fun setFirstDate() {
        editor.putString(KEY_FIRST_DATE, "2020-01-22")
        editor.commit()
    }

    fun getFirstDate(): String {
        return sharedPref.getString(KEY_FIRST_DATE, "2020-01-22")!!
    }
}