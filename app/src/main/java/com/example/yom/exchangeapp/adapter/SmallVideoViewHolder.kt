package com.example.yom.exchangeapp.adapter

import android.view.View
import android.widget.ImageView
import androidx.appcompat.widget.AppCompatImageView
import androidx.recyclerview.widget.RecyclerView
import com.example.yom.exchangeapp.R
import com.example.yom.exchangeapp.dto.SmallVideoDTO
import com.squareup.picasso.Picasso

class SmallVideoViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

    private val currentVideo by lazy { itemView.findViewById<ImageView>(R.id.imgThumbnail) }


    fun bindTo(trailer: SmallVideoDTO) {
        Picasso.get()
                .load(trailer.imgUrl)
                .resize(160, 90)
                .centerCrop()
                .into(itemView.findViewById<AppCompatImageView>(R.id.imgThumbnail))
        /*val videoList = ArrayList<YoutubeDTO>()
        videoList.add(YoutubeDTO("Cs992IBPIcA", "2019 Taslak Bütçesi\nVergiler"))
        videoList.add(YoutubeDTO("hesOSQ9tQeI", "Dünya Piyasalarında\nSon Durum"))
        videoList.add(YoutubeDTO("5eA8Sa6Q-k0", "Enflasyon&Faizler"))
        videoList.add(YoutubeDTO("F_CnCvSyzT4", "Tüketim Çöktü(Grafikli)"))
        itemView.setOnClickListener(object : View.OnClickListener{
            override fun onClick(view: View?) {
                Log.e("video_view_holder","$layoutPosition")
                VideoFragment.newInstance(videoList[layoutPosition].videoID, videoList[layoutPosition].videoName)
            }
        })*/
    }
}