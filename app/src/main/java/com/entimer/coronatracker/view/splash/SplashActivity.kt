package com.entimer.coronatracker.view.splash

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.entimer.coronatracker.R
import com.entimer.coronatracker.view.main.MainActivity

class SplashActivity: AppCompatActivity(), SplashContract.View {
    private lateinit var presenter: SplashPresenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)

        presenter = SplashPresenter(applicationContext, this)

        getData()
    }

    override fun getData() {
        presenter.getRecentData()
    }

    override fun onGetDataFinished() {
        val intent = Intent(applicationContext, MainActivity::class.java)
        startActivity(intent)
    }

    override fun onGetDataFailed() {
        Toast.makeText(applicationContext, "실패!", Toast.LENGTH_LONG).show()
        finish()
    }
}