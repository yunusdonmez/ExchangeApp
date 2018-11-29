package com.example.yom.exchangeapp.network.response

import retrofit2.Call
import retrofit2.http.GET

interface MoneyListService {

    @GET("items?marketId=1&type=0")
    fun getAll(): Call<List<MoneyListResponse>>
}