package io.github.entimer.coronatracker.ui.activity

import android.app.FragmentTransaction
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import io.github.entimer.coronatracker.R
import io.github.entimer.coronatracker.ui.fragment.ChartFragment
import io.github.entimer.coronatracker.ui.fragment.DashboardFragment
import io.github.entimer.coronatracker.ui.fragment.MapFragment
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity: AppCompatActivity() {
    private val dashboard = DashboardFragment()
    private val search = MapFragment()
    private val advice = ChartFragment()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val transaction: FragmentTransaction = fragmentManager.beginTransaction()
        transaction.replace(R.id.main_frame, dashboard).commitAllowingStateLoss()
        main_toolbar.title = getString(R.string.dashboard)

        main_toolbar.setOnMenuItemClickListener {
            when(it.itemId) {
                R.id.refresh -> {

                }
                R.id.search -> {
                    val intent = Intent(applicationContext, SearchActivity::class.java)
                    startActivity(intent)
                }
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
                R.id.map -> {
                    transaction.replace(R.id.main_frame, search).commitAllowingStateLoss()
                    main_toolbar.title = getString(R.string.map)
                }
                R.id.chart -> {
                    transaction.replace(R.id.main_frame, advice).commitAllowingStateLoss()
                    main_toolbar.title = getString(R.string.chart)
                }
            }
            true
        }
    }
}
