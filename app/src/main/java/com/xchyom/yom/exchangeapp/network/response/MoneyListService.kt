package com.xchyom.yom.exchangeapp.network.response

import retrofit2.Call
import retrofit2.http.GET

interface MoneyListService {

    @GET("")
    fun getAll(): Call<List<MoneyListResponse>>
}