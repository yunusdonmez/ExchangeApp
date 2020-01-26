package com.xchyom.yom.exchangeapp.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import android.view.animation.Animation
import android.view.animation.BounceInterpolator
import android.view.animation.ScaleAnimation
import android.widget.ImageView
import android.widget.TextView
import android.widget.ToggleButton
import androidx.recyclerview.widget.RecyclerView
import androidx.room.Room
import com.bumptech.glide.Glide
import com.xchyom.yom.exchangeapp.R
import com.xchyom.yom.exchangeapp.db.ExchangeDB
import com.xchyom.yom.exchangeapp.entity.ExchangeEntity
import java.math.RoundingMode
import java.text.DecimalFormat

class FavoriteViewHolder(parent: ViewGroup)
    : RecyclerView.ViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.adapter_item_favorite, parent, false)) {
    private val currencyType by lazy { itemView.findViewById<TextView>(R.id.txtCurrencyTypeFavorite) }
    private val valueBuying by lazy { itemView.findViewById<TextView>(R.id.txtValueBuyingFavorite) }
    private val valueSelling by lazy { itemView.findViewById<TextView>(R.id.txtValueSellingFavorite) }
    private val imgFlag by lazy { itemView.findViewById<ImageView>(R.id.imgFlagFavorite) }
    private val imgFollow by lazy { itemView.findViewById<ToggleButton>(R.id.imgFvrButton) }

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
        imgFollow.isChecked = exchangeDTO.isFollow
        val scaleAnimation = ScaleAnimation(0.7f, 1.0f, 0.7f, 1.0f, Animation.RELATIVE_TO_SELF, 0.7f, Animation.RELATIVE_TO_SELF, 0.7f)
        scaleAnimation.duration = 500
        val bounceInterpolator = BounceInterpolator()
        scaleAnimation.interpolator = bounceInterpolator
        imgFollow.setOnClickListener { view ->
            view?.startAnimation(scaleAnimation)
            val db = Room.databaseBuilder(
                    context.applicationContext,
                    ExchangeDB::class.java,
                    "exchangeDB"
            ).fallbackToDestructiveMigration().build()
            if (exchangeDTO.isFollow) {
                exchangeDTO.isFollow = false
                Thread {
                    //db.exchDao().insertAll(exchangeDTO)
                    db.exchDao().delete(exchangeDTO)
                }.start()
            } else {
                exchangeDTO.isFollow = true
                Thread {
                    //db.exchDao().insertAll(exchangeDTO)
                    db.exchDao().insertItem(exchangeDTO)
                }.start()
            }
            db.close()
        }
    }
}