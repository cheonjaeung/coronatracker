package io.github.entimer.coronatracker.ui.main

import android.app.FragmentTransaction
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import io.github.entimer.coronatracker.R
import io.github.entimer.coronatracker.ui.IView
import io.github.entimer.coronatracker.ui.search.SearchActivity
import io.github.entimer.coronatracker.ui.setting.SettingActivity
import io.github.entimer.coronatracker.ui.main.chart.ChartFragment
import io.github.entimer.coronatracker.ui.main.dashboard.DashboardFragment
import io.github.entimer.coronatracker.ui.main.map.MapFragment
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity: AppCompatActivity(), IView.Activity, IView.Frame, IMainMVP.View {
    private lateinit var presenter: MainPresenter

    private val dashboard = DashboardFragment()
    private val search = MapFragment()
    private val advice = ChartFragment()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        initPresenter()
        initView()
        initFragment()
        initListener()
    }

    override fun initPresenter() {
        presenter = MainPresenter(this)
    }

    override fun initView() {}

    override fun initListener() {
        main_toolbar.setOnMenuItemClickListener { item ->
            when(item.itemId) {
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

        main_bottomNav.setOnNavigationItemSelectedListener { item ->
            val transaction: FragmentTransaction = fragmentManager.beginTransaction()

            when(item.itemId) {
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

    override fun initFragment() {
        val transaction: FragmentTransaction = fragmentManager.beginTransaction()
        transaction.replace(R.id.main_frame, dashboard).commitAllowingStateLoss()
        main_toolbar.title = getString(R.string.dashboard)
    }
}
