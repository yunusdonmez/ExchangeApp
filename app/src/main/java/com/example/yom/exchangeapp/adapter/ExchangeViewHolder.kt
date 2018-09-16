package com.example.yom.exchangeapp.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.yom.exchangeapp.R
import com.example.yom.exchangeapp.dto.ExchangeDTO

class ExchangeViewHolder(parent: ViewGroup)
    : RecyclerView.ViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.adapter_item_exchange, parent, false)) {

    val currencyType by lazy { itemView.findViewById<TextView>(R.id.txtCurrencyType) }

    fun bindTo(exchangeDTO: ExchangeDTO) {
        currencyType.text = exchangeDTO.moneyType
    }
}