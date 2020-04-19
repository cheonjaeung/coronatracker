package com.entimer.coronatracker.ui.main.search

import android.app.Fragment
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import androidx.recyclerview.widget.LinearLayoutManager
import com.entimer.coronatracker.R
import com.entimer.coronatracker.api.iso3166.Iso3166Data
import com.entimer.coronatracker.ui.adapter.SearchListAdapter
import com.entimer.coronatracker.ui.base.IMvp
import kotlinx.android.synthetic.main.fragment_search.view.*

class SearchFragment: Fragment(), IMvp.View.Search {
    private lateinit var presenter: SearchPresenter

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view =  inflater!!.inflate(R.layout.fragment_search, container, false)

        presenter = SearchPresenter(this, view)

        initViews(view)

        return view
    }

    override fun initViews(view: View) {
        startLoading(view)
        initSearchList(view)
        stopLoading(view)
    }

    private fun initSearchList(view: View) {
        val list = view.search_list
        val adapter = SearchListAdapter()

        list.layoutManager = LinearLayoutManager(view.context)
        list.adapter = adapter

        adapter.updateList(arrayListOf(Iso3166Data("Name", "NA", "NAM", "001"),
            Iso3166Data("Name2", "NM", "NME", "002")))
    }

    override fun initListeners(view: View) {
    }

    override fun startLoading(view: View) {
        view.search_loading.startAnimation(AnimationUtils.loadAnimation(view.context, R.anim.anim_updating))
        view.search_loadingLayout.visibility = View.VISIBLE
    }

    override fun stopLoading(view: View) {
        view.search_loading.clearAnimation()
        view.search_loadingLayout.visibility = View.GONE
    }
}