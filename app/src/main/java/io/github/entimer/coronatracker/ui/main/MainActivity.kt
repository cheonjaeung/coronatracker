package io.github.entimer.coronatracker.ui.main

import android.app.FragmentTransaction
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import io.github.entimer.coronatracker.R
import io.github.entimer.coronatracker.ui.setting.SettingActivity
import io.github.entimer.coronatracker.ui.main.map.MapFragment
import io.github.entimer.coronatracker.ui.main.global.GlobalFragment
import io.github.entimer.coronatracker.ui.main.search.SearchFragment
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity: AppCompatActivity() {

    private val global = GlobalFragment()
    private val search = SearchFragment()
    private val map = MapFragment()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        initViews()
        initListeners()
    }

    private fun initViews() {
        val transaction: FragmentTransaction = fragmentManager.beginTransaction()
        transaction.replace(R.id.main_frame, global).commitAllowingStateLoss()
        main_toolbar.title = getString(R.string.global)
    }

    private fun initListeners() {
        main_toolbar.setOnMenuItemClickListener { item ->
            when(item.itemId) {
                R.id.setting -> {
                    val intent = Intent(applicationContext, SettingActivity::class.java)
                    startActivity(intent)
                }
            }
            true
        }

        main_bottomNav.setOnNavigationItemSelectedListener { item ->
            val transaction: FragmentTransaction = fragmentManager.beginTransaction()

            when(item.itemId) {
                R.id.global -> {
                    transaction.replace(R.id.main_frame, global).commitAllowingStateLoss()
                    main_toolbar.title = getString(R.string.global)
                }
                R.id.search -> {
                    transaction.replace(R.id.main_frame, search).commitAllowingStateLoss()
                    main_toolbar.title = getString(R.string.search)
                }
                R.id.map -> {
                    transaction.replace(R.id.main_frame, map).commitAllowingStateLoss()
                    main_toolbar.title = getString(R.string.map)
                }
            }
            true
        }
    }

    override fun onBackPressed() {
        finishAffinity()
    }
}
