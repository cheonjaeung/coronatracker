package io.github.entimer.coronatracker.ui.search

import android.os.Bundle
import android.view.inputmethod.EditorInfo
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import io.github.entimer.coronatracker.R
import io.github.entimer.coronatracker.ui.IView
import io.github.entimer.coronatracker.ui.adapter.SearchListAdapter
import kotlinx.android.synthetic.main.activity_search.*

class SearchActivity: AppCompatActivity(), IView.Activity {
    private lateinit var adapter: SearchListAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_search)

        initView()
        initListener()
    }
    override fun initView() {
        adapter = SearchListAdapter(arrayListOf())
        search_resultList.layoutManager = LinearLayoutManager(applicationContext)
        search_resultList.adapter = adapter
    }

    override fun initListener() {
        search_toolbar.setNavigationOnClickListener {
            finish()
        }

        search_searchBar.setOnEditorActionListener { view, actionId, event ->
            if(EditorInfo.IME_ACTION_SEARCH == actionId) {
                search()
            }
            else {
                false
            }
            true
        }
    }

    private fun search() {
        val keyword = search_searchBar.text.toString()
    }
}