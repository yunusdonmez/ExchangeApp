package com.example.yom.exchangeapp.adapter

import android.content.Context
import android.view.View
import android.view.ViewGroup
import android.widget.Filter
import android.widget.Filterable
import androidx.recyclerview.widget.RecyclerView
import com.example.yom.exchangeapp.dto.ExchangeDTO

class ExchangeAdapter : RecyclerView.Adapter<ExchangeViewHolder>, Filterable {
    private var list = ArrayList<ExchangeDTO>()
    private var listFiltered = ArrayList<ExchangeDTO>()
    lateinit var con: Context
    lateinit var rv: View

    constructor(list: ArrayList<ExchangeDTO>, con: Context) : super() {
        this.list = list
        listFiltered = list
        this.con = con
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ExchangeViewHolder = ExchangeViewHolder(parent)

    override fun getItemCount(): Int = list.size

    override fun onBindViewHolder(holder: ExchangeViewHolder, position: Int) {
        holder.bindTo(list[position])
        val number = 2
    }

    override fun getFilter(): Filter {
        return object : Filter() {
            override fun performFiltering(constraint: CharSequence?): FilterResults {
                var charString: String = constraint.toString()
                if (charString.isEmpty()) {
                    listFiltered = list
                } else {
                    var filteredList = ArrayList<ExchangeDTO>()
                    for (s: ExchangeDTO in list) {
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
                listFiltered = results!!.values as ArrayList<ExchangeDTO>
                notifyDataSetChanged()
            }
        }
    }
}