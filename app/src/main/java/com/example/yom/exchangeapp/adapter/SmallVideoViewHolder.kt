package com.example.yom.exchangeapp.adapter

import android.view.View
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.example.yom.exchangeapp.R
import com.example.yom.exchangeapp.dto.VideosDTO
import com.example.yom.exchangeapp.ui.VideoFragment
import com.squareup.picasso.Picasso

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
            //VideoFragment.newInstance(videoList[layoutPosition].videoID, videoList[layoutPosition].videoName)
        }
    }
}