package com.entimer.coronatracker.view.splash

import android.content.Context
import android.content.Intent
import android.net.ConnectivityManager
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.entimer.coronatracker.R
import com.entimer.coronatracker.view.main.MainActivity

class SplashActivity: AppCompatActivity(), SplashContract.View {
    private lateinit var presenter: SplashPresenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        if(!checkNetwork()) {
            Toast.makeText(applicationContext, getString(R.string.splashNetwordFailed), Toast.LENGTH_LONG).show()
            finishAffinity()
        }
        else {
            presenter = SplashPresenter(this)
            presenter.initCountryList(applicationContext)
        }
    }

    private fun checkNetwork(): Boolean {
        val manager = getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val networdInfo = manager.activeNetworkInfo
        if(networdInfo != null) {
            val type = networdInfo.type
            if(type == ConnectivityManager.TYPE_MOBILE || type == ConnectivityManager.TYPE_WIFI)
                return true
        }
        return false
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