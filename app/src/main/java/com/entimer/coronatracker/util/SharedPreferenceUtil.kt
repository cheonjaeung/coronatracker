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
}