package com.entimer.coronatracker.util

import android.content.Context

class SharedPreferenceUtil(context: Context) {
    private val fileName = "coronatrackersh"
    private val sharedPref = context.getSharedPreferences(fileName, Context.MODE_PRIVATE)
    private val editor = sharedPref.edit()

    fun setGlobalUpdated(date: String) {
        editor.putString("global_updated", date)
        editor.commit()
    }

    fun getGlobalUpdated(): String {
        return sharedPref.getString("global_updated", "2020-01-22-00-00-00")!!
    }

    fun setCountryUpdated(date: String, countryCode: String) {
        val code = countryCode.toLowerCase()
        editor.putString(code, date)
        editor.commit()
    }

    fun getCountryUpdated(countryCode: String): String {
        val code = countryCode.toLowerCase()
        return sharedPref.getString(code, "2020-01-22-00-00-00")!!
    }

    fun setIso3166Updated(value: Boolean) {
        editor.putBoolean("iso3166_updated", value)
        editor.commit()
    }

    fun getIso3166Updated(): Boolean {
        return sharedPref.getBoolean("iso3166_updated", false)
    }
}