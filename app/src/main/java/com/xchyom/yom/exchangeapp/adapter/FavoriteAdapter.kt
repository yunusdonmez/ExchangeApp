package com.xchyom.yom.exchangeapp.adapter

import android.content.Context
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.xchyom.yom.exchangeapp.entity.ExchangeEntity

class FavoriteAdapter(private var context: Context, private var moneyList: List<ExchangeEntity>) : RecyclerView.Adapter<FavoriteViewHolder>() {

    override fun onBindViewHolder(holder: FavoriteViewHolder, position: Int) {

        holder.bindTo(moneyList[position], context)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FavoriteViewHolder = FavoriteViewHolder(parent)

    override fun getItemCount(): Int = moneyList.size

    /*fun setNewFavoriteList(newMoneyList: List<ExchangeEntity>) {
        moneyList = newMoneyList
        notifyDataSetChanged()
    }*/
}