package com.example.yom.exchangeapp.adapter

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentStatePagerAdapter
import com.example.yom.exchangeapp.entity.YoutubeDTO
import com.example.yom.exchangeapp.ui.ExchangeFragment
import com.example.yom.exchangeapp.ui.FavoriteFragment
import com.example.yom.exchangeapp.ui.VideoFragment

class VideoPagerAdapter(fm: FragmentManager) : FragmentStatePagerAdapter(fm) {

    private var videoList: List<YoutubeDTO> = ArrayList()

    override fun getItem(position: Int): Fragment {
        return when (position) {
            0 -> {
                ExchangeFragment()
            }

            1 -> {
                FavoriteFragment()
            }

            else -> {
                VideoFragment.newInstance(videoList[position - 2].videoID, videoList[position - 2].videoName)
            }
        }
    }

    override fun getCount(): Int {
        return videoList.size + 2
        //return 3
    }

    fun setVideoList(videoList: List<YoutubeDTO>) {
        this.videoList = videoList
        notifyDataSetChanged()
    }

    override fun getPageTitle(position: Int): CharSequence? {
        return when (position) {
            0 -> "Döviz"
            1 -> "Takip Ettiklerin"
            else -> "Finans Yorumları"
        }
    }
}