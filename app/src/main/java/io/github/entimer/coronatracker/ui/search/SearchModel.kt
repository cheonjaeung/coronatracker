package io.github.entimer.coronatracker.ui.search

import android.content.Context
import android.os.AsyncTask
import android.os.Handler
import io.github.entimer.coronatracker.api.country.Country
import io.github.entimer.coronatracker.room.AppDatabase

class SearchModel(presenter: SearchPresenter) {
    private val presenter = presenter

    class BackgroundTask(presenter: SearchPresenter, context: Context, keyword: String): AsyncTask<String, String, String>() {
        private val presenter = presenter
        private val context = context
        private val keyword = keyword
        private val list = ArrayList<Country>()

        override fun doInBackground(vararg params: String?): String {
            val db = AppDatabase.getDatabase(context)

            val countries = db.countryDao().selectByKeyword("%$keyword%")

            for(country in countries) {
                list.add(Country(country.code!!, country.name))
            }

            db.close()

            return "Success"
        }

        override fun onPostExecute(result: String?) {
            presenter.updateView(list)
        }
    }

    fun getData(context: Context, keyword: String) {
        val task = BackgroundTask(presenter, context, keyword)
        task.execute()
    }
}