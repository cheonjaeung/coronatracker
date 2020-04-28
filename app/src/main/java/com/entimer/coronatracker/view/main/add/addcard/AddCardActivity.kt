package com.entimer.coronatracker.view.main.add.addcard

import android.os.Bundle
import android.view.View
import android.view.inputmethod.EditorInfo
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.entimer.coronatracker.R
import com.entimer.coronatracker.data.dataclass.CountryData
import kotlinx.android.synthetic.main.activity_add_card.*

class AddCardActivity : AppCompatActivity(), AddCardContract.View {
    private lateinit var presenter: AddCardPresenter

    override var allCountries =  ArrayList<CountryData>()
    private var isSearching = false
    private lateinit var adapter: AddCardListAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_card)

        presenter = AddCardPresenter(this)

        initViews()

        presenter.getCountryList()
    }

    private fun initViews() {
        addCardToolbar.setNavigationOnClickListener {
            finish()
        }

        addCardToolbar.setOnMenuItemClickListener { item ->
            if(item.itemId == R.id.search) {
                if(isSearching) {
                    search()
                }
                else {
                    isSearching = true
                    addCardToolbarTitle.visibility = View.GONE
                    addCardSearchBar.visibility = View.VISIBLE
                }
                true
            }
            false
        }

        addCardSearchBar.setOnEditorActionListener { v, actionId, event ->
            if(actionId == EditorInfo.IME_ACTION_SEARCH) {
                search()
                true
            }
            false
        }

        adapter = AddCardListAdapter()
        addCardCountryList.adapter = adapter
        addCardCountryList.layoutManager = LinearLayoutManager(applicationContext)
    }

    override fun onBackPressed() {
        if(isSearching) {
            isSearching = false
            addCardToolbarTitle.visibility = View.VISIBLE
            addCardSearchBar.visibility = View.GONE
            addCardSearchBar.text.clear()
        }
        else {
            super.onBackPressed()
        }
    }

    private fun search() {
        val keyword = addCardSearchBar.text.toString()
    }
}