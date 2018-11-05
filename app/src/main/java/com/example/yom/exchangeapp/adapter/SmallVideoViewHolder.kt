package com.example.yom.exchangeapp.adapter

import android.view.View
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.example.yom.exchangeapp.R
import com.example.yom.exchangeapp.dto.SmallVideoDTO
import com.example.yom.exchangeapp.entity.YoutubeDTO
import com.example.yom.exchangeapp.ui.VideoFragment
import com.squareup.picasso.Picasso

class SmallVideoViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

    fun bindTo(trailer: SmallVideoDTO, fragment: VideoFragment) {
        Picasso.get()
                .load(trailer.imgUrl)
                .resize(240, 180)
                .centerCrop()
                .into(itemView.findViewById<ImageView>(R.id.imgThumbnail))
        //VideoFragment.newInstance(videoList[position - 2].videoID, videoList[position - 2].videoName)

        val videoList = ArrayList<YoutubeDTO>()
        videoList.add(YoutubeDTO("Cs992IBPIcA", "2019 Taslak Bütçesi\nVergiler"))
        videoList.add(YoutubeDTO("hesOSQ9tQeI", "Dünya Piyasalarında\nSon Durum"))
        videoList.add(YoutubeDTO("5eA8Sa6Q-k0", "Enflasyon&Faizler"))
        videoList.add(YoutubeDTO("F_CnCvSyzT4", "Tüketim Çöktü(Grafikli)"))
        itemView.setOnClickListener(object : View.OnClickListener {
            override fun onClick(view: View?) {
                fragment.resetProperties()
                fragment.currentVideoTitle = videoList[layoutPosition].videoName
                fragment.currentVideo = videoList[layoutPosition].videoID
                fragment.initializePlayer()
                //Log.e("video_view_holder","${fragment.CURRENT_WINDOW_INDEX}")
                //VideoFragment.newInstance(videoList[layoutPosition].videoID, videoList[layoutPosition].videoName)
            }
        })
    }
}