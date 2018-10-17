package com.example.yom.exchangeapp.ui

import android.annotation.SuppressLint
import android.os.AsyncTask
import android.os.Bundle
import android.view.*
import android.widget.SearchView
import androidx.core.view.MenuItemCompat
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.room.Room
import com.example.yom.exchangeapp.R
import com.example.yom.exchangeapp.adapter.ExchangeAdapter
import com.example.yom.exchangeapp.db.ExchangeDB
import com.example.yom.exchangeapp.entity.ExchangeEntity
import kotlinx.android.synthetic.main.fragment_exchange.*
import org.json.JSONArray
import java.net.URL
import javax.net.ssl.HttpsURLConnection


class ExchangeFragment : Fragment() {

    lateinit var searchView: SearchView
    lateinit var adapter: ExchangeAdapter
    private val exchangeList = ArrayList<ExchangeEntity>()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        setHasOptionsMenu(true)
        return inflater.inflate(R.layout.fragment_exchange, container, false)

    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        val url = "https://www.doviz.com/api/v1/currencies/all/latest"
        AsyncTaskHandleJson().execute(url)
    }

    override fun onCreateOptionsMenu(menu: Menu?, inflater: MenuInflater?) {
        inflater!!.inflate(R.menu.menu_main, menu)
        val item: MenuItem = menu!!.findItem(R.id.actionSearch)
        searchView = MenuItemCompat.getActionView(item) as SearchView

        searchName(searchView)
    }

    override fun onDestroyOptionsMenu() {
        rcyExchange.setHasFixedSize(true)
        rcyExchange.layoutManager = LinearLayoutManager(activity)
        adapter = ExchangeAdapter(exchangeList, rcyExchange.context)
        rcyExchange.adapter = adapter
        super.onDestroyOptionsMenu()
    }

    private fun searchName(searchView: SearchView) {
        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                adapter.filter.filter(newText)
                return true
            }
        })
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        if (item!!.itemId == R.id.actionSearch) {
            return true
        }
        return super.onOptionsItemSelected(item)
    }

    @SuppressLint("StaticFieldLeak")
    inner class AsyncTaskHandleJson : AsyncTask<String, String, String>() {
        override fun doInBackground(vararg url: String?): String {
            val connection = URL(url[0]).openConnection() as HttpsURLConnection
            var text: String
            try {
                connection.connect()
                text = connection.inputStream.use { it.reader().use { reader -> reader.readText() } }
            } finally {
                connection.disconnect()
            }
            return text
        }

        override fun onPostExecute(result: String?) {
            super.onPostExecute(result)
            handleJson(result)
        }
    }

    private fun handleJson(jsonstring: String?) {
        val jsonArray = JSONArray(jsonstring)
        var x = 0
        val db = Room.databaseBuilder(
                context!!.applicationContext,
                ExchangeDB::class.java,
                "exchangeDB"
        ).fallbackToDestructiveMigration().build()
        Thread {
            while (x < jsonArray.length()) {
                val jsonObject = jsonArray.getJSONObject(x)
                if (db.exchDao().getItemCounts(jsonObject.getString("code")) > 0) {
                    exchangeList.add(ExchangeEntity(
                            jsonObject.getString("code"),
                            jsonObject.getString("name"),
                            jsonObject.getString("selling"),
                            jsonObject.getString("buying"),
                            "https://coinyep.com/img/png/" + jsonObject.getString("code") + ".png",
                            true)
                    )
                } else {
                    exchangeList.add(ExchangeEntity(
                            jsonObject.getString("code"),
                            jsonObject.getString("name"),
                            jsonObject.getString("selling"),
                            jsonObject.getString("buying"),
                            "https://coinyep.com/img/png/" + jsonObject.getString("code") + ".png",
                            false)
                    )
                }
                x++
            }
        }.start()

        rcyExchange.setHasFixedSize(true)
        rcyExchange.layoutManager = LinearLayoutManager(activity)
        adapter = ExchangeAdapter(exchangeList, rcyExchange.context)
        rcyExchange.adapter = adapter
        /* rcyExchange.apply {
             this.setHasFixedSize(true)
             this.layoutManager = LinearLayoutManager(activity)
             adapter = ExchangeAdapter(exchangeList, context)
             this.adapter = adapter
         }*/
    }
}

