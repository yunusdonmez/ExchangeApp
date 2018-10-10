package com.example.yom.exchangeapp.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.yom.exchangeapp.R
import com.example.yom.exchangeapp.adapter.ExchangePagerAdapter
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val fragmentAdapter = ExchangePagerAdapter(supportFragmentManager)
        vpMain.adapter = fragmentAdapter
        tabsMain.setupWithViewPager(vpMain)
    }

}
