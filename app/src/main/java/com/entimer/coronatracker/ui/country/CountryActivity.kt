package com.entimer.coronatracker.ui.country

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.entimer.coronatracker.R
import kotlinx.android.synthetic.main.activity_country.*

class CountryActivity: AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_country)

        initViews()
        initListeners()
    }

    private fun initViews() {
        val intent = intent
        val name = intent.getStringExtra("name")
        val code = intent.getStringExtra("code")
        country_toolbar.title = name
    }

    private fun initListeners() {
        country_toolbar.setNavigationOnClickListener {
            finish()
        }
    }
}