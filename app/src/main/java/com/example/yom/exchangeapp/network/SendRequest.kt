package com.example.yom.exchangeapp.network

import com.example.yom.exchangeapp.network.response.MoneyListResponse
import com.example.yom.exchangeapp.network.response.MoneyListService
import retrofit2.Call

object SendRequest {
    fun getAll(): Call<List<MoneyListResponse>> = RetrofitClient.getClient().create(MoneyListService::class.java).getAll()
}