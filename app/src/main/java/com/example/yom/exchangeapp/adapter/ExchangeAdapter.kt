package com.example.yom.exchangeapp.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.yom.exchangeapp.R
import com.example.yom.exchangeapp.dto.ExchangeDTO
import java.math.RoundingMode
import java.text.DecimalFormat

class ExchangeAdapter : RecyclerView.Adapter<ExchangeAdapter.Companion.Holder>, Filterable {


    private var list = ArrayList<ExchangeDTO>()
    private var listFiltered = ArrayList<ExchangeDTO>()
    lateinit var con: Context
    lateinit var rv: View

    constructor(list: ArrayList<ExchangeDTO>, con: Context) : super() {
        this.list = list
        listFiltered = list
        this.con = con
    }

    companion object {
        class Holder : RecyclerView.ViewHolder {
            lateinit var currencyType: TextView
            lateinit var valueBuying: TextView
            lateinit var valueSelling: TextView
            lateinit var imgFlag: ImageView

            constructor(rv: View) : super((rv)) {
                currencyType = rv.findViewById(R.id.txtCurrencyType) as TextView
                valueBuying = rv.findViewById(R.id.txtValueBuying) as TextView
                valueSelling = rv.findViewById(R.id.txtValueSelling) as TextView
                imgFlag = rv.findViewById(R.id.imgFlag) as ImageView
            }
        }

    }

    override fun onBindViewHolder(holder: Holder, position: Int) {
        val exchangeDTO = listFiltered[position]
        holder.currencyType.text = exchangeDTO.moneyType.toUpperCase().replace("-", " ")
        val num = exchangeDTO.valueSelling.toDouble()
        val num2 = exchangeDTO.valueBuying.toDouble()
        val df = DecimalFormat("#.###")
        df.roundingMode = RoundingMode.CEILING
        holder.valueSelling.text = "Satış : " + df.format(num).toString() + " TL"
        holder.valueBuying.text = "Alış : " + df.format(num2).toString() + " TL"
        Glide.with(con).load(exchangeDTO.flag).into(holder.imgFlag)
        rv.setOnClickListener { Toast.makeText(con, holder.currencyType.text, Toast.LENGTH_LONG).show() }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder {
        var holder: Holder
        rv = LayoutInflater.from(parent.context).inflate(R.layout.adapter_item_exchange, parent, false)
        holder = Holder(rv)
        return holder
    }

    override fun getItemCount(): Int = listFiltered.size

    /* override fun onBindViewHolder(holder: ExchangeViewHolder, position: Int) {
         holder.bindTo(moneyList[position])
         val number = 2
     }*/
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

    fun updateList(newList: ArrayList<ExchangeDTO>) {

    }
}