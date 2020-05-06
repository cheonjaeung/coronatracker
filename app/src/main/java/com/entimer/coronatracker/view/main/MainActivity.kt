package com.entimer.coronatracker.view.main

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.entimer.coronatracker.R
import com.entimer.coronatracker.view.tip.TipActivity
import kotlinx.android.synthetic.main.activity_main.*

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

        mainToolbar.setOnMenuItemClickListener {item ->
            if(item.itemId == R.id.help) {
                val intent = Intent(applicationContext, TipActivity::class.java)
                startActivity(intent)
                true
            }
            false
        }
    }

    override fun setList(list: ArrayList<MainCardListItem>) {
        adapter.updateList(list)
    }
}