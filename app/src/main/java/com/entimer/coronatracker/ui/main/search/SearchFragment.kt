package com.entimer.coronatracker.ui.main.search

import android.app.Fragment
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import android.view.inputmethod.EditorInfo
import androidx.recyclerview.widget.LinearLayoutManager
import com.entimer.coronatracker.R
import com.entimer.coronatracker.api.iso3166.Iso3166Data
import com.entimer.coronatracker.ui.adapter.SearchListAdapter
import kotlinx.android.synthetic.main.fragment_search.view.*

class SearchFragment: Fragment() {
    private lateinit var presenter: SearchPresenter

    private val adapter = SearchListAdapter()

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view =  inflater!!.inflate(R.layout.fragment_search, container, false)

        presenter = SearchPresenter(this, view)

        initViews(view)

        return view
    }

    private fun initViews(view: View) {
        startLoading(view)
        initSearchList(view)
        stopLoading(view)
    }

    private fun initSearchList(view: View) {
        val list = view.search_list
        list.layoutManager = LinearLayoutManager(view.context)
        list.adapter = adapter
    }

    fun initListeners(view: View) {
        view.search_searchBar.setOnEditorActionListener { view, actionId, event ->
            if(actionId == EditorInfo.IME_ACTION_SEARCH) {
                presenter.getData(view.context, view.text.toString())
                true
            }
            false
        }
    }

    fun updateSearchList(view: View, list: ArrayList<Iso3166Data>) {
        adapter.updateList(list)
    }

    fun startLoading(view: View) {
        view.search_loading.startAnimation(AnimationUtils.loadAnimation(view.context, R.anim.anim_updating))
        view.search_loadingLayout.visibility = View.VISIBLE
    }

    fun stopLoading(view: View) {
        view.search_loading.clearAnimation()
        view.search_loadingLayout.visibility = View.GONE
    }
}