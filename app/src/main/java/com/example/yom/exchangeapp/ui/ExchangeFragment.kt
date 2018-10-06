package com.example.yom.exchangeapp.ui

import android.os.AsyncTask
import android.os.Bundle
import android.view.*
import android.widget.SearchView
import androidx.appcompat.widget.Toolbar
import androidx.core.view.MenuItemCompat
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.yom.exchangeapp.R
import com.example.yom.exchangeapp.adapter.ExchangeAdapter
import com.example.yom.exchangeapp.dto.ExchangeDTO
import kotlinx.android.synthetic.main.fragment_exchange.*
import org.json.JSONArray
import java.net.URL
import javax.net.ssl.HttpsURLConnection


class ExchangeFragment : Fragment() {

    lateinit var toolbar: Toolbar
    lateinit var searchView: SearchView
    lateinit var adapter: ExchangeAdapter


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
        var item: MenuItem = menu!!.findItem(R.id.actionSearch)
        searchView = MenuItemCompat.getActionView(item) as SearchView
        /*MenuItemCompat.setOnActionExpandListener(item, object : MenuItemCompat.OnActionExpandListener {
            override fun onMenuItemActionExpand(item: MenuItem?): Boolean {
                (searchView.findViewById(androidx.appcompat.R.id.search_src_text) as? AppCompatEditText)?.setHintTextColor(resources.getColor(R.color.colorLayout))
                (searchView.findViewById(androidx.appcompat.R.id.search_src_text) as? AppCompatEditText)?.setHintTextColor(resources.getColor(R.color.colorLayout))
                (searchView.findViewById(androidx.appcompat.R.id.search_src_text) as? AppCompatEditText)?.hint = "Ara"
                searchView.isIconified = true
                searchView.setQuery("", false)
                searchView.maxWidth = Int.MAX_VALUE
                searchName(searchView)
                return true
            }

            override fun onMenuItemActionCollapse(item: MenuItem?): Boolean {
                searchView.setQuery("", false)
                return true
            }
        })*/
        searchName(searchView)
    }

    fun searchName(searchView: SearchView) {
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
        val exchangeList = ArrayList<ExchangeDTO>()
        var x = 0
        while (x < jsonArray.length()) {
            val jsonObject = jsonArray.getJSONObject(x)
            exchangeList.add(ExchangeDTO(
                    jsonObject.getString("name"),
                    jsonObject.getString("selling"),
                    jsonObject.getString("buying"),
                    "https://coinyep.com/img/png/" + jsonObject.getString("code") + ".png"
            ))
            x++
        }
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

