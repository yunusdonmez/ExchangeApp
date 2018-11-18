package com.example.yom.exchangeapp.ui


import android.os.Bundle
import android.util.Log
import android.view.*
import android.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.yom.exchangeapp.R
import com.example.yom.exchangeapp.adapter.ExchangeAdapter
import com.example.yom.exchangeapp.entity.ExchangeEntity
import com.example.yom.exchangeapp.model.ExchangeViewModel
import com.example.yom.exchangeapp.network.SendRequest
import com.example.yom.exchangeapp.network.response.MoneyListResponse
import kotlinx.android.synthetic.main.fragment_exchange.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ExchangeFragment : Fragment(), Callback<List<MoneyListResponse>> {


    lateinit var searchView: SearchView
    lateinit var adapter: ExchangeAdapter
    var exchangeList = ArrayList<ExchangeEntity>()
    lateinit var exchangeViewModel: ExchangeViewModel
    override fun onFailure(call: Call<List<MoneyListResponse>>, t: Throwable) {
        Log.e("Retrofit", "$t")
    }

    override fun onResponse(call: Call<List<MoneyListResponse>>, response: Response<List<MoneyListResponse>>) {
        var i = 0
        exchangeList.clear()
        while (i < response.body()!!.size) {
            if (exchangeViewModel.getItemCounts(response.body()!![i].code) > 0) {
                exchangeList.add(ExchangeEntity(
                        response.body()!![i].code,
                        response.body()!![i].moneyType,
                        response.body()!![i].valueSelling,
                        response.body()!![i].valueBuying,
                        "https://coinyep.com/img/png/" + response.body()!![i].code + ".png",
                        true))
            } else {
                exchangeList.add(ExchangeEntity(
                        response.body()!![i].code,
                        response.body()!![i].moneyType,
                        response.body()!![i].valueSelling,
                        response.body()!![i].valueBuying,
                        "https://coinyep.com/img/png/" + response.body()!![i].code + ".png",
                        false))
            }
            i++
        }
        rcyExchange.setHasFixedSize(true)
        rcyExchange.layoutManager = LinearLayoutManager(activity)
        adapter = ExchangeAdapter(exchangeList, rcyExchange.context)
        rcyExchange.adapter = adapter
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        setHasOptionsMenu(true)
        return inflater.inflate(R.layout.fragment_exchange, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        // val url = "https://www.doviz.com/api/v1/currencies/all/latest"
        exchangeViewModel = ViewModelProviders.of(this).get(ExchangeViewModel::class.java)
        pulToRefresh.setOnRefreshListener {
            pulToRefresh.isRefreshing = false
            SendRequest.getAll().enqueue(this@ExchangeFragment)
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?, inflater: MenuInflater?) {
        SendRequest.getAll().enqueue(this)
        inflater!!.inflate(R.menu.menu_main, menu)
        val item: MenuItem = menu!!.findItem(R.id.actionSearch)

        searchView = item.actionView as SearchView
        searchName(searchView)
    }

    override fun onDestroyOptionsMenu() {
        rcyExchange.setHasFixedSize(true)
        rcyExchange.layoutManager = LinearLayoutManager(activity)
        rcyExchange.itemAnimator = DefaultItemAnimator()
        adapter = ExchangeAdapter(exchangeList, rcyExchange.context)
        rcyExchange.adapter = adapter
        super.onDestroyOptionsMenu()
    }

    private fun searchName(searchView: SearchView) {
        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                adapter.filter.filter(newText)
                return true
            }
        })
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        if (item!!.itemId == R.id.actionSearch) {
            return true
        }
        return super.onOptionsItemSelected(item)
    }
}

