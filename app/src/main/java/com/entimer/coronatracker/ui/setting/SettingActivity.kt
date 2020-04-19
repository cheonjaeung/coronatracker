package com.entimer.coronatracker.ui.setting

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.entimer.coronatracker.R
import kotlinx.android.synthetic.main.activity_setting.*

class SettingActivity: AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_setting)

        setting_toolbar.setNavigationOnClickListener {
            finish()
        }
    }
}