package com.xchyom.yom.exchangeapp.ui


import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.*
import android.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.LinearLayoutManager
import com.xchyom.yom.exchangeapp.R
import com.xchyom.yom.exchangeapp.adapter.ExchangeAdapter
import com.xchyom.yom.exchangeapp.entity.CrossValuesEntity
import com.xchyom.yom.exchangeapp.entity.ExchangeEntity
import com.xchyom.yom.exchangeapp.network.SendRequest
import com.xchyom.yom.exchangeapp.network.response.MoneyListResponse
import com.xchyom.yom.exchangeapp.viewmodel.CrossValuesViewModel
import com.xchyom.yom.exchangeapp.viewmodel.ExchangeViewModel
import kotlinx.android.synthetic.main.fragment_exchange.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ExchangeFragment : Fragment(), Callback<List<MoneyListResponse>> {


    private lateinit var searchView: SearchView
    lateinit var adapter: ExchangeAdapter
    private var exchangeList = ArrayList<ExchangeEntity>()
    private var crossValuesList = ArrayList<CrossValuesEntity>()

    private lateinit var exchangeViewModel: ExchangeViewModel
    private lateinit var crossValuesViewModel: CrossValuesViewModel


    override fun onFailure(call: Call<List<MoneyListResponse>>, t: Throwable) {
        Log.e("Retrofit", "$t")
    }

    override fun onResponse(call: Call<List<MoneyListResponse>>, response: Response<List<MoneyListResponse>>) {
        var i = 0
        exchangeList.clear()
        crossValuesList.clear()
        //Log.e("retrofit", "Response geldi")
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
            crossValuesList.add(CrossValuesEntity(
                    response.body()!![i].code,
                    response.body()!![i].moneyType,
                    response.body()!![i].valueSelling,
                    response.body()!![i].valueBuying))
            i++
        }
        crossValuesViewModel.insert(crossValuesList)
        //Log.e("retrofit", "Response diziye atildii")
        rcyExchange.setHasFixedSize(true)
        rcyExchange.layoutManager = LinearLayoutManager(activity)
        adapter = ExchangeAdapter(exchangeList, rcyExchange.context)
        rcyExchange.adapter = adapter
        //Log.e("retrofit", "Recyclerview dolduruldu.")

    }

    lateinit var layout: View
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        setHasOptionsMenu(true)
        layout = inflater.inflate(R.layout.fragment_exchange, container, false)
        return layout
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        exchangeViewModel = ViewModelProviders.of(this).get(ExchangeViewModel::class.java)
        crossValuesViewModel = ViewModelProviders.of(this).get(CrossValuesViewModel::class.java)

        pulToRefresh.setOnRefreshListener {
            pulToRefresh.isRefreshing = false
            SendRequest.getAll().enqueue(this@ExchangeFragment)
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?, inflater: MenuInflater?) {
        //Log.e("retrofit", "Request.")
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
        return when (item?.itemId) {
            R.id.about -> {
                val intent = Intent(context, AboutActivity::class.java)
                startActivityForResult(intent, 1)
                true
            }
            R.id.cross -> {
                alertDialog()
                true
            }
            R.id.refresh -> {
                SendRequest.getAll().enqueue(this@ExchangeFragment)
                true
            }
            R.id.actionSearch -> {
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    fun alertDialog() {
        val crossFragment = CrossFragment()
        crossFragment.show(fragmentManager, "cross_fragment")
    }
}

