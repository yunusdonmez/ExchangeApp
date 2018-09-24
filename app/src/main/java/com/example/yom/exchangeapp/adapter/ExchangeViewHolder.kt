package com.example.yom.exchangeapp.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.yom.exchangeapp.R
import com.example.yom.exchangeapp.dto.ExchangeDTO
import java.math.RoundingMode
import java.text.DecimalFormat

class ExchangeViewHolder(parent: ViewGroup)
    : RecyclerView.ViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.adapter_item_exchange, parent, false)) {

    private val currencyType by lazy { itemView.findViewById<TextView>(R.id.txtCurrencyType) }
    private val valueBuying by lazy { itemView.findViewById<TextView>(R.id.txtValueBuying) }
    private val valueSelling by lazy { itemView.findViewById<TextView>(R.id.txtValueSelling) }
    private val imgFlag by lazy { itemView.findViewById<ImageView>(R.id.imgFlag) }
    fun bindTo(exchangeDTO: ExchangeDTO) {
        currencyType.text = exchangeDTO.moneyType.toUpperCase().replace("-", " ")
        val num = exchangeDTO.valueSelling.toDouble()
        val num2 = exchangeDTO.valueBuying.toDouble()
        val df = DecimalFormat("#.##")
        df.roundingMode = RoundingMode.CEILING
        valueSelling.text = "Satış : " + df.format(num).toString() + " TL"
        valueBuying.text = "Alış : " + df.format(num2).toString() + " TL"
        Glide.with(itemView.context).load(exchangeDTO.flag).into(imgFlag)
    }
}