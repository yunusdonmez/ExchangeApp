package com.example.yom.exchangeapp.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.yom.exchangeapp.R
import com.example.yom.exchangeapp.dto.SmallVideoDTO

class SmallVideoAdapter(private val trailerList: ArrayList<SmallVideoDTO>) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {

        return SmallVideoViewHolder(LayoutInflater.from(parent.context)
                .inflate(R.layout.adapter_item_small_video, parent, false))
    }

    override fun getItemCount(): Int = trailerList.size

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        (holder as SmallVideoViewHolder).bindTo(trailerList[position])
    }
}