package com.example.yom.exchangeapp.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.yom.exchangeapp.R
import com.example.yom.exchangeapp.adapter.VideoPagerAdapter
import com.example.yom.exchangeapp.dto.VideosDTO
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    /*private val exchangeList = ArrayList<ExchangeEntity>()
    private lateinit var exchangeViewModel: ExchangeViewModel
    private lateinit var exchangePagerAdapter: ExchangePagerAdapter*/
    private lateinit var videoPagerAdapter: VideoPagerAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        /*exchangePagerAdapter = ExchangePagerAdapter(supportFragmentManager)
        vpMain.adapter = exchangePagerAdapter
        tabsMain.setupWithViewPager(vpMain)*/

        videoPagerAdapter = VideoPagerAdapter(supportFragmentManager)
        vpMain.apply {
            adapter = videoPagerAdapter
            offscreenPageLimit = 3
        }

        val videoList = ArrayList<VideosDTO>()
        videoList.add(VideosDTO("Cs992IBPIcA", "2019 Taslak Bütçesi\nVergiler", "ss"))
        //videoList.add(YoutubeDTO("hesOSQ9tQeI", "Dünya Piyasalarında\nSon Durum"))
        // videoList.add(YoutubeDTO("5eA8Sa6Q-k0", "Enflasyon&Faizler"))
        //videoList.add(YoutubeDTO("F_CnCvSyzT4", "Tüketim Çöktü(Grafikli)"))


        (vpMain.adapter as VideoPagerAdapter).setVideoList(videoList)
        tabsMain.setupWithViewPager(vpMain)

    }

}
