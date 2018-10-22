package com.example.yom.exchangeapp.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProviders
import androidx.viewpager.widget.ViewPager
import com.example.yom.exchangeapp.R
import com.example.yom.exchangeapp.adapter.ExchangePagerAdapter
import com.example.yom.exchangeapp.adapter.FavoriteAdapter
import com.example.yom.exchangeapp.model.ExchangeViewModel
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.fragment_favourite.*

class MainActivity : AppCompatActivity() {
    lateinit var adapter: FavoriteAdapter
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
                    /*rcyFavorite.layoutManager = LinearLayoutManager(this@MainActivity)
                    adapter = FavoriteAdapter { Toast.makeText(this@MainActivity, "naber", Toast.LENGTH_SHORT).show() }
                    rcyFavorite.adapter = adapter*/
                    /*exchangeViewModel = ViewModelProviders.of(this@MainActivity).get(ExchangeViewModel::class.java)
                    exchangeViewModel.updateDatas()
                    exchangeViewModel.allList.observe(this@MainActivity, Observer<List<ExchangeEntity>> {
                        (rcyFavorite.adapter as FavoriteAdapter).setNewFavoriteList(it)
                        adapter.notifyDataSetChanged()

                    })*/
                    exchangeViewModel = ViewModelProviders.of(this@MainActivity).get(ExchangeViewModel::class.java)
                    exchangeViewModel.updateDatas()
                    FavoriteFragment().updateFragment(rcyFavorite, exchangeViewModel, this@MainActivity)
                } else if (position == 0) {

                }
            }

        })
    }

}
