package com.entimer.coronatracker.view.main

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.entimer.coronatracker.R

class MainActivity: AppCompatActivity(), MainContract.View {
    private lateinit var presenter: MainPresenter

    private lateinit var adapter: MainCardListAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        presenter = MainPresenter(applicationContext, this)

        initViews()

        presenter.getList()
    }

    override fun onResume() {
        super.onResume()
        presenter.getList()
    }

    private fun initViews() {
        adapter = MainCardListAdapter()
        val list = findViewById<RecyclerView>(R.id.mainCardList)
        list.adapter = adapter
        list.layoutManager = LinearLayoutManager(applicationContext)
    }

    override fun setList(list: ArrayList<MainCardListItem>) {
        adapter.updateList(list)
    }
}