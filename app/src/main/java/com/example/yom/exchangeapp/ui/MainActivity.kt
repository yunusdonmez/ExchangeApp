package com.example.yom.exchangeapp.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProviders
import androidx.viewpager.widget.ViewPager
import com.example.yom.exchangeapp.R
import com.example.yom.exchangeapp.adapter.ExchangeAdapter
import com.example.yom.exchangeapp.adapter.ExchangePagerAdapter
import com.example.yom.exchangeapp.entity.ExchangeEntity
import com.example.yom.exchangeapp.model.ExchangeViewModel
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.fragment_favourite.*

class MainActivity : AppCompatActivity() {
    lateinit var adapter: ExchangeAdapter
    private val exchangeList = ArrayList<ExchangeEntity>()
    lateinit var exchangeViewModel: ExchangeViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val fragmentAdapter = ExchangePagerAdapter(supportFragmentManager)
        vpMain.adapter = fragmentAdapter
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
