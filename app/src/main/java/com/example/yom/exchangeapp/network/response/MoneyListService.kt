package com.example.yom.exchangeapp.network.response

import retrofit2.Call
import retrofit2.http.GET

interface MoneyListService {

    @GET("latest")
    fun getAll(): Call<List<MoneyListResponse>>
}