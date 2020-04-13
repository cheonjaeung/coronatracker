package io.github.entimer.coronatracker.ui

import android.view.View

interface IView {
    interface Activity {
        fun initPresenter()
        fun initView()
        fun initListener()
    }

    interface Frame {
        fun initFragment()
    }

    interface Fragment {
        fun initPresenter()
        fun initView(view: View)
        fun initListener(view: View)
    }
}