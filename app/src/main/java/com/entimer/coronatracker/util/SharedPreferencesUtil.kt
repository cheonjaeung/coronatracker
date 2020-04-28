package com.entimer.coronatracker.util

import android.content.Context

class SharedPreferencesUtil(context: Context) {
    companion object {
        const val KEY_COUNTRY_LIST_INITIALIZED = "isCountryListInitialized"
        const val DEFAULT_COUNTRY_LIST_INITIALIZED = false
    }

    private val preferencesName = "corona_tracker_preferences"
    private val context = context
    private val preferences = context.getSharedPreferences(preferencesName, Context.MODE_PRIVATE)

    fun setBoolean(key: String, value: Boolean) {
        val editor = preferences.edit()
        editor.putBoolean(key, value)
        editor.commit()
    }

    fun getBoolean(key: String, default: Boolean): Boolean = preferences.getBoolean(key, default)
}