package com.example.yom.exchangeapp.ui

import android.os.AsyncTask
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
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
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_exchange, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        val url = "https://www.doviz.com/api/v1/currencies/all/latest"
        AsyncTaskHandleJson().execute(url)


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

        rcyExchange.apply {
            this.layoutManager = LinearLayoutManager(activity)
            this.adapter = ExchangeAdapter(exchangeList)
        }
    }


}
