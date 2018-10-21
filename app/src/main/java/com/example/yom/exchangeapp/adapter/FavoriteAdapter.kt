package com.example.yom.exchangeapp.adapter

import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.yom.exchangeapp.entity.ExchangeEntity
import java.util.*

class FavoriteAdapter(val onItemSelect: (exchangeEntity: ExchangeEntity) -> Unit) : RecyclerView.Adapter<FavoriteViewHolder>() {

    private var moneyList: List<ExchangeEntity> = Collections.emptyList()

    override fun onBindViewHolder(holder: FavoriteViewHolder, position: Int) {

        holder.bindTo(moneyList[position])
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FavoriteViewHolder = FavoriteViewHolder(parent)

    override fun getItemCount(): Int = moneyList.size

    fun setNewFavoriteList(newMoneyList: List<ExchangeEntity>) {
        moneyList = newMoneyList
        notifyDataSetChanged()
    }
}