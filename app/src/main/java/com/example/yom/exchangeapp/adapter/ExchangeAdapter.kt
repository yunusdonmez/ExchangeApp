package com.example.yom.exchangeapp.adapter

import android.content.Context
import android.view.ViewGroup
import android.widget.Filter
import android.widget.Filterable
import androidx.recyclerview.widget.RecyclerView
import com.example.yom.exchangeapp.entity.ExchangeEntity

class ExchangeAdapter : RecyclerView.Adapter<ExchangeViewHolder>, Filterable {
    private var list = ArrayList<ExchangeEntity>()
    private var listFiltered = ArrayList<ExchangeEntity>()
    var con: Context

    constructor(list: ArrayList<ExchangeEntity>, con: Context) : super() {
        this.list = list
        listFiltered = list
        this.con = con
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ExchangeViewHolder = ExchangeViewHolder(parent)

    override fun getItemCount(): Int = listFiltered.size

    override fun onBindViewHolder(holder: ExchangeViewHolder, position: Int) {
        holder.bindTo(listFiltered[position], con)
    }

    override fun getFilter(): Filter {
        return object : Filter() {
            override fun performFiltering(constraint: CharSequence?): FilterResults {
                var charString: String = constraint.toString()
                if (charString.isEmpty()) {
                    listFiltered = list
                } else {
                    var filteredList = ArrayList<ExchangeEntity>()
                    for (s: ExchangeEntity in list) {
                        if (s.moneyType.toLowerCase().contains(charString.toLowerCase())) {
                            filteredList.add(s)
                        }
                    }
                    listFiltered = filteredList
                }
                var filterResults = FilterResults()
                filterResults.values = listFiltered
                return filterResults
            }

            override fun publishResults(p0: CharSequence?, results: FilterResults?) {
                listFiltered = results!!.values as ArrayList<ExchangeEntity>
                notifyDataSetChanged()
            }
        }
    }
}