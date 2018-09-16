package com.example.yom.exchangeapp.ui

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


class ExchangeFragment : Fragment() {
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_exchange, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        val exchangeList = ArrayList<ExchangeDTO>()
        exchangeList.add(ExchangeDTO("Dolar"))
        exchangeList.add(ExchangeDTO("Euro"))
        exchangeList.add(ExchangeDTO("Altın"))

        rcyExchange.apply {
            this.layoutManager = LinearLayoutManager(activity)
            this.adapter = ExchangeAdapter(exchangeList)
        }
    }


}
