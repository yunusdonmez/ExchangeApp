package com.example.yom.exchangeapp.ui


import android.annotation.SuppressLint
import android.os.AsyncTask
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.SearchView
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.AppCompatEditText
import androidx.appcompat.widget.Toolbar
import androidx.core.view.MenuItemCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.yom.exchangeapp.R
import com.example.yom.exchangeapp.adapter.ExchangeAdapter
import com.example.yom.exchangeapp.dto.ExchangeDTO
import org.json.JSONArray
import java.net.URL
import javax.net.ssl.HttpsURLConnection

class MainActivity : AppCompatActivity() {

    lateinit var recyclerView: RecyclerView
    private val exchangeList = ArrayList<ExchangeDTO>()
    private var listFiltered = ArrayList<ExchangeDTO>()
    lateinit var layoutManager: RecyclerView.LayoutManager
    lateinit var toolbar: Toolbar
    lateinit var searchView: SearchView
    lateinit var adapter: ExchangeAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val url = "https://www.doviz.com/api/v1/currencies/all/latest"
        //  val text= "[{\"selling\":6.0048,\"update_date\":1538531944,\"currency\":1,\"buying\":5.9988,\"change_rate\":0.28809780294111,\"name\":\"amerikan-dolari\",\"full_name\":\"Amerikan Dolar\u0131\",\"code\":\"USD\"},{\"selling\":6.9458,\"update_date\":1538531954,\"currency\":2,\"buying\":6.9357,\"change_rate\":0.40620437428625,\"name\":\"euro\",\"full_name\":\"Euro\",\"code\":\"EUR\"}]"
        //handleJson(text)
        AsyncTaskHandleJson().execute(url)
        toolbar = findViewById<Toolbar>(R.id.toolbar)
        setSupportActionBar(toolbar)
        recyclerView = findViewById<RecyclerView>(R.id.recyclerview)
        layoutManager = LinearLayoutManager(this)
        recyclerView.layoutManager = layoutManager
        recyclerView.setHasFixedSize(true)
        adapter = ExchangeAdapter(exchangeList, this)
        recyclerView.adapter = adapter

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
        listFiltered = exchangeList
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_main, menu)
        var item: MenuItem = menu!!.findItem(R.id.actionSearch)
        searchView = MenuItemCompat.getActionView(item) as SearchView
        toolbar.setBackgroundColor(resources.getColor(R.color.colorPrimary))

        MenuItemCompat.setOnActionExpandListener(item, object : MenuItemCompat.OnActionExpandListener {
            override fun onMenuItemActionExpand(item: MenuItem?): Boolean {
                //toolbar.setBackgroundColor(Color.WHITE)
                //searchView.setBackgroundColor(Color.BLACK)
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
                toolbar.setBackgroundColor(resources.getColor(R.color.colorPrimary))
                searchView.setQuery("", false)
                return true
            }
        })


        return true
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

    override fun onBackPressed() {
        if (!searchView.isIconified) {
            searchView.isIconified = true
            return
        }
        super.onBackPressed()
    }
}
