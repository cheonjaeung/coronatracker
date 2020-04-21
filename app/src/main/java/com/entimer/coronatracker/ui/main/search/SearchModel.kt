package com.entimer.coronatracker.ui.main.search

import android.content.Context
import android.os.AsyncTask
import android.util.Log
import com.entimer.coronatracker.api.iso3166.Iso3166Data
import com.entimer.coronatracker.room.AppDatabase
import java.lang.Exception

class SearchModel(presenter: SearchPresenter) {
    private val presenter = presenter

    fun getCountries(context: Context, keyword: String) {
        val task = SearchModelAsyncTask(context, presenter, keyword)
        task.execute()
    }

    class SearchModelAsyncTask(context: Context, presenter: SearchPresenter, keyword: String): AsyncTask<String, String, String>() {
        companion object {
            private const val SUCCESS = "success"
            private const val FAILURE = "failure"
        }

        private val logTag = "SearchModelAsyncTask"
        private val context = context
        private val presenter = presenter
        private val keyword = keyword
        private val list = ArrayList<Iso3166Data>()

        override fun doInBackground(vararg params: String?): String {
            val db = AppDatabase.getDatabase(context)

            try {
                val data = db.iso3166Dao().selectByKeyword(keyword)

                for(entity in data) {
                    list.add(Iso3166Data(entity.name, entity.alpha2, entity.alpha3, entity.numeric))
                }
            }
            catch(e: Exception) {
                Log.e(logTag, "Room database select failure:")
                e.printStackTrace()
                return FAILURE
            }
            return SUCCESS
        }

        override fun onPostExecute(result: String?) {
            if(result == SUCCESS) {
                presenter.updateSearchList(list)
            }
            else {
                presenter.updateSearchList(list)
            }
        }
    }
}