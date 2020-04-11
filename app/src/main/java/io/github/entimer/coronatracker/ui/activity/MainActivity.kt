package io.github.entimer.coronatracker.ui.activity

import android.app.FragmentTransaction
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import io.github.entimer.coronatracker.R
import io.github.entimer.coronatracker.ui.fragment.AdviceFragment
import io.github.entimer.coronatracker.ui.fragment.DashboardFragment
import io.github.entimer.coronatracker.ui.fragment.SearchFragment
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity: AppCompatActivity() {
    private val dashboard = DashboardFragment()
    private val search = SearchFragment()
    private val advice = AdviceFragment()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val transaction: FragmentTransaction = fragmentManager.beginTransaction()
        transaction.replace(R.id.main_frame, dashboard).commitAllowingStateLoss()
        main_toolbar.title = getString(R.string.dashboard)

        main_toolbar.setOnMenuItemClickListener {
            when(it.itemId) {
                R.id.setting -> {
                    val intent = Intent(applicationContext, SettingActivity::class.java)
                    startActivity(intent)
                }
            }
            true
        }

        main_bottomNav.setOnNavigationItemSelectedListener {
            val transaction: FragmentTransaction = fragmentManager.beginTransaction()

            when(it.itemId) {
                R.id.dashboard -> {
                    transaction.replace(R.id.main_frame, dashboard).commitAllowingStateLoss()
                    main_toolbar.title = getString(R.string.dashboard)
                }
                R.id.search -> {
                    transaction.replace(R.id.main_frame, search).commitAllowingStateLoss()
                    main_toolbar.title = getString(R.string.search)
                }
                R.id.advice -> {
                    transaction.replace(R.id.main_frame, advice).commitAllowingStateLoss()
                    main_toolbar.title = getString(R.string.advice)
                }
            }
            true
        }
    }
}
