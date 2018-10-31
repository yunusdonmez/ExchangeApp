package com.example.yom.exchangeapp.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProviders
import androidx.viewpager.widget.ViewPager
import com.example.yom.exchangeapp.R
import com.example.yom.exchangeapp.adapter.ExchangePagerAdapter
import com.example.yom.exchangeapp.adapter.VideoPagerAdapter
import com.example.yom.exchangeapp.entity.ExchangeEntity
import com.example.yom.exchangeapp.entity.YoutubeDTO
import com.example.yom.exchangeapp.model.ExchangeViewModel
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.fragment_favourite.*

class MainActivity : AppCompatActivity() {
    private val exchangeList = ArrayList<ExchangeEntity>()
    private lateinit var exchangeViewModel: ExchangeViewModel
    private lateinit var videoPagerAdapter: VideoPagerAdapter
    private lateinit var exchangePagerAdapter: ExchangePagerAdapter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        /*exchangePagerAdapter = ExchangePagerAdapter(supportFragmentManager)
        vpMain.adapter = exchangePagerAdapter
        tabsMain.setupWithViewPager(vpMain)*/

        videoPagerAdapter = VideoPagerAdapter(supportFragmentManager)
        vpMain.apply {
            adapter = videoPagerAdapter
            offscreenPageLimit = 1
        }

        val videoList = ArrayList<YoutubeDTO>()
        videoList.add(YoutubeDTO("Cs992IBPIcA", "2019 Taslak Bütçesi\nVergiler"))
        videoList.add(YoutubeDTO("hesOSQ9tQeI", "Dünya Piyasalarında\nSon Durum"))
        videoList.add(YoutubeDTO("5eA8Sa6Q-k0", "Enflasyon&Faizler"))
        videoList.add(YoutubeDTO("F_CnCvSyzT4", "Tüketim Çöktü(Grafikli)"))


        (vpMain.adapter as VideoPagerAdapter).setVideoList(videoList)
        tabsMain.setupWithViewPager(vpMain)
        vpMain.addOnPageChangeListener(object : ViewPager.OnPageChangeListener {
            override fun onPageScrollStateChanged(state: Int) {
            }

            override fun onPageScrolled(position: Int, positionOffset: Float, positionOffsetPixels: Int) {
            }

            override fun onPageSelected(position: Int) {
                if (position == 1) {
                    exchangeViewModel = ViewModelProviders.of(this@MainActivity).get(ExchangeViewModel::class.java)
                    exchangeViewModel.updateDatas()
                    FavoriteFragment().updateFragment(rcyFavorite, exchangeViewModel, this@MainActivity)
                } else if (position == 0) {
                    exchangeList.clear()
                    exchangeViewModel.updateDatas()
                }
            }
        })
    }

}
