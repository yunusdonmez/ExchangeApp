package com.example.yom.exchangeapp.adapter

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import com.example.yom.exchangeapp.ui.ExchangeFragment
import com.example.yom.exchangeapp.ui.FavoriteFragment

class ExchangePagerAdapter(fragmentManager: FragmentManager) : FragmentPagerAdapter(fragmentManager) {
    override fun getItem(position: Int): Fragment {
        return when (position) {
            0 -> {
                ExchangeFragment()
            }

            else -> {
                FavoriteFragment()
            }
        }
    }

    override fun getCount() = 2

    override fun getPageTitle(position: Int): CharSequence? {
        return when (position) {
            0 -> {
                "Döviz"
            }

            else -> "Takip Ettiklerin"
        }
    }
}