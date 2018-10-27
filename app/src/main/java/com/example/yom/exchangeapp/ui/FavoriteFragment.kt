package com.example.yom.exchangeapp.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.yom.exchangeapp.R
import com.example.yom.exchangeapp.adapter.FavoriteAdapter
import com.example.yom.exchangeapp.entity.ExchangeEntity
import com.example.yom.exchangeapp.model.ExchangeViewModel
import kotlinx.android.synthetic.main.fragment_favourite.*


class FavoriteFragment : Fragment() {


    lateinit var adapter: FavoriteAdapter
    lateinit var exchangeViewModel: ExchangeViewModel


    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        exchangeViewModel = ViewModelProviders.of(this).get(ExchangeViewModel::class.java)
        updateFragment(rcyFavorite, exchangeViewModel, activity!!)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_favourite, container, false)
    }

    fun updateFragment(favoriteRcy: RecyclerView, exchangeViewModel: ExchangeViewModel, activity: FragmentActivity) {
        favoriteRcy.layoutManager = LinearLayoutManager(activity)
        adapter = FavoriteAdapter(favoriteRcy.context)
        favoriteRcy.adapter = adapter
        //exchangeViewModel.updateDatas()
        exchangeViewModel.allList.observe(activity, Observer<List<ExchangeEntity>> {
            (favoriteRcy.adapter as FavoriteAdapter).setNewFavoriteList(it)
            adapter.notifyDataSetChanged()

        })
    }

}
