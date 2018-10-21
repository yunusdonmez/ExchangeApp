package com.example.yom.exchangeapp.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
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
        rcyFavorite.layoutManager = LinearLayoutManager(activity)
        adapter = FavoriteAdapter { Toast.makeText(activity, "naber", Toast.LENGTH_SHORT).show() }
        rcyFavorite.adapter = adapter
        exchangeViewModel = ViewModelProviders.of(this).get(ExchangeViewModel::class.java)
        exchangeViewModel.allList.observe(this, Observer<List<ExchangeEntity>> {
            (rcyFavorite.adapter as FavoriteAdapter).setNewFavoriteList(it)
            adapter.notifyDataSetChanged()

        })
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_favourite, container, false)
    }

}
