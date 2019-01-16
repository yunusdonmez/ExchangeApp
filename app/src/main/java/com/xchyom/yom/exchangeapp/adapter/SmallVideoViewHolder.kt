package com.xchyom.yom.exchangeapp.adapter

import android.view.View
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso
import com.xchyom.yom.exchangeapp.R
import com.xchyom.yom.exchangeapp.dto.VideosDTO
import com.xchyom.yom.exchangeapp.ui.VideoFragment

class SmallVideoViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

    fun bindTo(videoList: VideosDTO, fragment: VideoFragment) {
        Picasso.get()
                .load(videoList.videoPic)
                .resize(240, 180)
                .centerCrop()
                .into(itemView.findViewById<ImageView>(R.id.imgThumbnail))

        itemView.setOnClickListener {
            fragment.resetProperties()
            fragment.currentVideoTitle = videoList.viodeTitle
            fragment.currentVideo = videoList.videoLink
            fragment.initializePlayer()
            //Log.e("video_view_holder","${fragment.CURRENT_WINDOW_INDEX}")
        }
    }
}