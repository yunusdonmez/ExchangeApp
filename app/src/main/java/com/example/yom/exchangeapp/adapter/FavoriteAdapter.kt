package com.example.yom.exchangeapp.adapter

import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.yom.exchangeapp.dto.ExchangeDTO

class FavoriteAdapter(private val list: ArrayList<ExchangeDTO>) : RecyclerView.Adapter<FavoriteViewHolder>() {
    override fun onBindViewHolder(holder: FavoriteViewHolder, position: Int) {
        holder.bindTo(list[position])
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FavoriteViewHolder = FavoriteViewHolder(parent)

    override fun getItemCount(): Int = list.size

}