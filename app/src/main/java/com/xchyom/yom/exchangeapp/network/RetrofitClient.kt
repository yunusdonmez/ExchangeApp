package com.xchyom.yom.exchangeapp.network

import retrofit2.Retrofit
import retrofit2.converter.jackson.JacksonConverterFactory

class RetrofitClient {

    companion object {
        private const val BASE_URL = "***"

        fun getClient(): Retrofit {

            return with(Retrofit.Builder()) {
                this.baseUrl(BASE_URL)
                this.addConverterFactory(JacksonConverterFactory.create())
                this.build()
            }
        }
    }
}