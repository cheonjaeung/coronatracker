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

        presenter = SplashPresenter(this)
        presenter.initCountryList(applicationContext)
    }

    override fun onInitFinished() {
        val intent = Intent(applicationContext, MainActivity::class.java)
        startActivity(intent)
        finish()
    }

    override fun onInitFailed() {
        Toast.makeText(applicationContext, getString(R.string.splashInitFailed), Toast.LENGTH_LONG).show()
        finishAffinity()
    }
}