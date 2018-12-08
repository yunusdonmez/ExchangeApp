package com.example.yom.exchangeapp.ui

import android.content.Intent
import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.yom.exchangeapp.R
import com.example.yom.exchangeapp.adapter.FavoriteAdapter
import com.example.yom.exchangeapp.entity.ExchangeEntity
import com.example.yom.exchangeapp.viewmodel.ExchangeViewModel
import kotlinx.android.synthetic.main.fragment_favourite.*

class FavoriteFragment : Fragment() {

    lateinit var adapter: FavoriteAdapter
    private lateinit var exchangeViewModel: ExchangeViewModel

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        updateFragment()
        pulToRefreshFav.setOnRefreshListener {
            pulToRefreshFav.isRefreshing = false
            updateFragment()
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        setHasOptionsMenu(true)
        return inflater.inflate(R.layout.fragment_favourite, container, false)
    }

    private fun updateFragment() {
        exchangeViewModel = ViewModelProviders.of(this).get(ExchangeViewModel::class.java)
        exchangeViewModel.updateDatas()
        var list: List<ExchangeEntity>
        exchangeViewModel.allList.observe(this, Observer<List<ExchangeEntity>> {
            list = it
            rcyFavorite.setHasFixedSize(true)
            rcyFavorite.layoutManager = LinearLayoutManager(activity)
            adapter = FavoriteAdapter(rcyFavorite.context, list)
            rcyFavorite.adapter = adapter
        })
    }

    override fun setUserVisibleHint(isVisibleToUser: Boolean) {
        super.setUserVisibleHint(isVisibleToUser)
        if (isVisibleToUser)
            updateFragment()
    }

    override fun onCreateOptionsMenu(menu: Menu?, inflater: MenuInflater?) {
        inflater?.inflate(R.menu.menu_main, menu)
        val item: MenuItem = menu!!.findItem(R.id.actionSearch)
        item.isVisible = false
        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        return when (item?.itemId) {
            R.id.about -> {
                val intent = Intent(context, AboutActivity::class.java)
                startActivityForResult(intent, 1)
                true
            }
            R.id.refresh -> {
                updateFragment()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }
}
