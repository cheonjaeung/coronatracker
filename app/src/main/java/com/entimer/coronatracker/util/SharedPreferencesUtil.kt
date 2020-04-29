package com.entimer.coronatracker.util

import android.content.Context
import com.entimer.coronatracker.view.main.MainCardListItem

class SharedPreferencesUtil(context: Context) {
    companion object {
        const val KEY_COUNTRY_LIST_INITIALIZED = "isCountryListInitialized"
        const val DEFAULT_COUNTRY_LIST_INITIALIZED = false

        const val KEY_CARD_LIST = "cardList"
    }

    private val preferencesName = "corona_tracker_preferences"
    private val context = context
    private val preferences = context.getSharedPreferences(preferencesName, Context.MODE_PRIVATE)
    private val editor = preferences.edit()

    fun setBoolean(key: String, value: Boolean) {
        editor.putBoolean(key, value)
        editor.commit()
    }

    fun getBoolean(key: String, default: Boolean): Boolean = preferences.getBoolean(key, default)

    fun setCard(cards: ArrayList<MainCardListItem>) {
        val temp = ArrayList<String>()

        for(card in cards)
            temp.add("${card.viewType}-${card.option}")

        val value = temp.toMutableSet()

        editor.putStringSet(KEY_CARD_LIST, value)
        editor.commit()
    }

    fun getCard(): ArrayList<MainCardListItem> {
        val value = preferences.getStringSet(KEY_CARD_LIST, mutableSetOf())!!.toList()
        val cards = ArrayList<MainCardListItem>()

        for(item in value) {
            val data = item.split("-")
            val viewType = data[0].toInt()
            val option = data[1]
            cards.add(MainCardListItem(viewType, option))
        }

        return cards
    }
}