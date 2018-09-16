package com.example.yom.exchangeapp.adapter

import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.yom.exchangeapp.dto.ExchangeDTO

class ExchangeAdapter(private var moneyList: List<ExchangeDTO>) : RecyclerView.Adapter<ExchangeViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ExchangeViewHolder = ExchangeViewHolder(parent)

    override fun getItemCount(): Int = moneyList.size

    override fun onBindViewHolder(holder: ExchangeViewHolder, position: Int) {
        holder.bindTo(moneyList[position])
    }
}