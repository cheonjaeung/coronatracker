package io.github.entimer.coronatracker.ui.search

import android.os.Bundle
import android.view.inputmethod.EditorInfo
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import io.github.entimer.coronatracker.R
import io.github.entimer.coronatracker.api.country.Country
import io.github.entimer.coronatracker.ui.IView
import io.github.entimer.coronatracker.ui.adapter.SearchListAdapter
import kotlinx.android.synthetic.main.activity_search.*

class SearchActivity: AppCompatActivity(), IView.Activity, ISearchMVP.View {
    private lateinit var presenter: SearchPresenter
    private lateinit var adapter: SearchListAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_search)

        initPresenter()
        initView()
        initListener()
    }

    override fun initPresenter() {
        presenter = SearchPresenter(this)
    }

    override fun initView() {
        adapter = SearchListAdapter(
                arrayListOf(Country(getString(R.string.country_code),
                    getString(R.string.country_name)
                )
            )
        )
        search_list.layoutManager = LinearLayoutManager(applicationContext)
        search_list.adapter = adapter
    }

    override fun initListener() {
        search_toolbar.setNavigationOnClickListener {
            finish()
        }

        search_search_button.setOnClickListener {
            search()
        }

        search_search_input.setOnEditorActionListener { view, actionId, event ->
            if(EditorInfo.IME_ACTION_SEARCH == actionId) {
                search()
            }
            else {
                false
            }
            true
        }
    }

    override fun updateView(countries: ArrayList<Country>) {
        adapter.updateList(countries)
    }

    private fun search() {
        val keyword = search_search_input.text.toString()
        presenter.getData(applicationContext, keyword)
    }
}