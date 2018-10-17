package com.example.yom.exchangeapp.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.room.Room
import com.example.yom.exchangeapp.R
import com.example.yom.exchangeapp.adapter.FavoriteAdapter
import com.example.yom.exchangeapp.db.ExchangeDB
import com.example.yom.exchangeapp.entity.ExchangeEntity
import kotlinx.android.synthetic.main.fragment_favourite.*


class FavoriteFragment : Fragment() {

    lateinit var adapter: FavoriteAdapter
    private var exchangeList = ArrayList<ExchangeEntity>()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {

        val db = Room.databaseBuilder(
                context!!.applicationContext,
                ExchangeDB::class.java,
                "exchangeDB"
        ).fallbackToDestructiveMigration().build()
        Thread {
            exchangeList = ArrayList(db.exchDao().getAll())
            if (rcyFavorite != null) {
                rcyFavorite.layoutManager = LinearLayoutManager(activity)
                adapter = FavoriteAdapter(exchangeList, rcyFavorite.context)
                rcyFavorite.adapter = adapter
            }
        }.start()
        return inflater.inflate(R.layout.fragment_favourite, container, false)

    }


}
