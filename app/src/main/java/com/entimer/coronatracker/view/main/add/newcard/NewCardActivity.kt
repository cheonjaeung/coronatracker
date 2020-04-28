package com.entimer.coronatracker.view.main.add.newcard

import android.os.Bundle
import android.view.View
import android.view.inputmethod.EditorInfo
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.entimer.coronatracker.R
import com.entimer.coronatracker.data.dataclass.CountryData
import kotlinx.android.synthetic.main.activity_new_card.*

class NewCardActivity : AppCompatActivity(), NewCardContract.View {
    private lateinit var presenter: NewCardPresenter

    override var allCountries =  ArrayList<CountryData>()

    private var isSearching = false
    private lateinit var adapter: NewCardListAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_new_card)

        presenter = NewCardPresenter(this)

        initViews()

        presenter.getCountryList()
    }

    private fun initViews() {
        newCardToolbar.setNavigationOnClickListener {
            finish()
        }

        newCardToolbar.setOnMenuItemClickListener { item ->
            if(item.itemId == R.id.search) {
                if(isSearching) {
                    search()
                }
                else {
                    isSearching = true
                    newCardToolbarTitle.visibility = View.GONE
                    newCardSearchBar.visibility = View.VISIBLE
                }
                true
            }
            false
        }

        newCardSearchBar.setOnEditorActionListener { v, actionId, event ->
            if(actionId == EditorInfo.IME_ACTION_SEARCH) {
                search()
                true
            }
            false
        }

        adapter = NewCardListAdapter()
        newCardCountryList.adapter = adapter
        newCardCountryList.layoutManager = LinearLayoutManager(applicationContext)
    }

    override fun onBackPressed() {
        if(isSearching) {
            isSearching = false
            newCardToolbarTitle.visibility = View.VISIBLE
            newCardSearchBar.visibility = View.GONE
            newCardSearchBar.text.clear()
        }
        else {
            super.onBackPressed()
        }
    }

    private fun search() {
        val keyword = newCardSearchBar.text.toString()
        val newList = ArrayList<CountryData>()

        for(country in allCountries) {
            val countryName = country.name.toLowerCase()
            val lowerKeyword = keyword.toLowerCase()
            if(countryName.contains(lowerKeyword))
                newList.add(country)
        }

        setList(newList)
    }

    override fun setList(data: ArrayList<CountryData>) {
        adapter.updateList(data)
    }
}