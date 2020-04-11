package io.github.entimer.coronatracker.ui.activity

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import io.github.entimer.coronatracker.R
import kotlinx.android.synthetic.main.activity_setting.*

class SearchActivity: AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_setting)

        setting_toolbar.setNavigationOnClickListener {
            finish()
        }
    }
}