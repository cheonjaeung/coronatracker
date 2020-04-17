package io.github.entimer.coronatracker.ui.search

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import io.github.entimer.coronatracker.R
import io.github.entimer.coronatracker.ui.adapter.SearchListAdapter

class SearchActivity: AppCompatActivity() {
    private lateinit var adapter: SearchListAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_search)
    }
}