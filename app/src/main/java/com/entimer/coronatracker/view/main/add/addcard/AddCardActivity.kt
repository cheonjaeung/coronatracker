package com.entimer.coronatracker.view.main.add.addcard

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.entimer.coronatracker.R
import kotlinx.android.synthetic.main.activity_add_card.*

class AddCardActivity: AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_card)

        initViews()
    }

    private fun initViews() {
        addCardToolbar.setNavigationOnClickListener {
            finish()
        }
    }
}