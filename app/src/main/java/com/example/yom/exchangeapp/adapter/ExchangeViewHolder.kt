package com.example.yom.exchangeapp.adapter

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import androidx.room.Room
import com.bumptech.glide.Glide
import com.example.yom.exchangeapp.R
import com.example.yom.exchangeapp.db.ExchangeDB
import com.example.yom.exchangeapp.entity.ExchangeEntity
import java.math.RoundingMode
import java.text.DecimalFormat

class ExchangeViewHolder(parent: ViewGroup)
    : RecyclerView.ViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.adapter_item_exchange, parent, false)) {

    private val currencyType by lazy { itemView.findViewById<TextView>(R.id.txtCurrencyType) }
    private val valueBuying by lazy { itemView.findViewById<TextView>(R.id.txtValueBuying) }
    private val valueSelling by lazy { itemView.findViewById<TextView>(R.id.txtValueSelling) }
    private val imgFlag by lazy { itemView.findViewById<ImageView>(R.id.imgFlag) }
    private val imgFollow by lazy { itemView.findViewById<ImageView>(R.id.imgFavoriteButton) }
    fun bindTo(exchangeDTO: ExchangeEntity, context: Context) {
        currencyType.text = exchangeDTO.moneyType.toUpperCase().replace("-", " ")
        val num = exchangeDTO.valueSelling.toDouble()
        val num2 = exchangeDTO.valueBuying.toDouble()
        val df = DecimalFormat("#.####")
        df.roundingMode = RoundingMode.CEILING
        valueSelling.text = "Satış : " + df.format(num).toString() + " TL"
        valueBuying.text = "Alış : " + df.format(num2).toString() + " TL"
        Glide.with(itemView.context).load(exchangeDTO.flag).into(imgFlag)

        //favori kontrol
        /* val db = Room.databaseBuilder(
                 context.applicationContext,
                 ExchangeDB::class.java,
                 "exchangeDB"
         ).fallbackToDestructiveMigration().build()
         var control=0
         Thread{
             //db.exchDao().insertAll(exchangeDTO)
             control=db.exchDao().getItemCounts(exchangeDTO.code)
             Log.i("yunus","""Tıklanan ${exchangeDTO.code} Gelen Count:${control}""")
         }.start()*/
        if (exchangeDTO.isFollow) {
            Log.i("yunus", """Kontrole girildi ${exchangeDTO.code} """)
            imgFollow.setBackgroundResource(R.drawable.ic_favorite)
        } else {
            Log.i("yunus", """Kontrole girilmedi ${exchangeDTO.code} """)
            imgFollow.setBackgroundResource(R.drawable.ic_non_favorite)
        }
        //db.close()

        //Favorilere ekleme çıkarma
        imgFollow.setOnClickListener {
            val db2 = Room.databaseBuilder(
                    context.applicationContext,
                    ExchangeDB::class.java,
                    "exchangeDB"
            ).fallbackToDestructiveMigration().build()
            if (exchangeDTO.isFollow) {
                it.setBackgroundResource(R.drawable.ic_non_favorite)
                exchangeDTO.isFollow = false
                Thread {
                    //db.exchDao().insertAll(exchangeDTO)
                    db2.exchDao().delete(exchangeDTO)
                    Log.i("yunus", "Item silindi:${exchangeDTO.code}")
                }.start()

            } else {
                exchangeDTO.isFollow = true
                it.setBackgroundResource(R.drawable.ic_favorite)
                Thread {
                    //db.exchDao().insertAll(exchangeDTO)
                    db2.exchDao().insertItem(exchangeDTO)
                    Log.i("yunus", "Favorilere eklendi:${exchangeDTO.code}")
                }.start()
            }
            db2.close()
        }
    }
}