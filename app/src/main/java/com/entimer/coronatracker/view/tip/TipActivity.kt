package com.entimer.coronatracker.view.tip

import android.app.Activity
import android.os.Bundle
import android.view.MotionEvent
import android.view.Window
import com.entimer.coronatracker.R
import kotlinx.android.synthetic.main.activity_tip.*

class TipActivity: Activity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        requestWindowFeature(Window.FEATURE_NO_TITLE)
        setContentView(R.layout.activity_tip)

        tipOkButton.setOnClickListener {
            finish()
        }
    }

    override fun onTouchEvent(event: MotionEvent?): Boolean {
        return event!!.action != MotionEvent.ACTION_OUTSIDE
    }

    override fun onBackPressed() {
        return
    }
}