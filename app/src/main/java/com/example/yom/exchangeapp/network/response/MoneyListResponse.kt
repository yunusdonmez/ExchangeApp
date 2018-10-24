package com.example.yom.exchangeapp.network.response

import com.example.yom.exchangeapp.entity.ExchangeEntity
import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import com.fasterxml.jackson.annotation.JsonProperty

@JsonIgnoreProperties(ignoreUnknown = true)
class MoneyListResponse(){
    private val moneyList: ExchangeEntity? = null
}