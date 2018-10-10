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

class FavoriteViewHolder(parent: ViewGroup)
    : RecyclerView.ViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.adapter_item_favorite, parent, false)) {
    private val currencyType by lazy { itemView.findViewById<TextView>(R.id.txtCurrencyTypeFavorite) }
    private val valueBuying by lazy { itemView.findViewById<TextView>(R.id.txtValueBuyingFavorite) }
    private val valueSelling by lazy { itemView.findViewById<TextView>(R.id.txtValueSellingFavorite) }
    private val imgFlag by lazy { itemView.findViewById<ImageView>(R.id.imgFlagFavorite) }
    private val imgFollow by lazy { itemView.findViewById<ImageView>(R.id.imgFavoriteButtonFavorite) }

    fun bindTo(favoriteDTO: ExchangeDTO) {
        currencyType.text = favoriteDTO.moneyType.toUpperCase().replace("-", " ")
        val num = favoriteDTO.valueSelling.toDouble()
        val num2 = favoriteDTO.valueBuying.toDouble()
        val df = DecimalFormat("#.####")
        df.roundingMode = RoundingMode.CEILING
        valueSelling.text = "Satış : " + df.format(num).toString() + " TL"
        valueBuying.text = "Alış : " + df.format(num2).toString() + " TL"
        Glide.with(itemView.context).load(favoriteDTO.flag).into(imgFlag)
        if (favoriteDTO.isFollow) {
            imgFollow.setBackgroundResource(R.drawable.ic_favorite)
        } else {
            imgFollow.setBackgroundResource(R.drawable.ic_non_favorite)
        }
        imgFollow.setOnClickListener {
            if (favoriteDTO.isFollow) {
                it.setBackgroundResource(R.drawable.ic_non_favorite)
                favoriteDTO.isFollow = false
            } else {
                it.setBackgroundResource(R.drawable.ic_favorite)
                favoriteDTO.isFollow = true
            }

        }
    }
}