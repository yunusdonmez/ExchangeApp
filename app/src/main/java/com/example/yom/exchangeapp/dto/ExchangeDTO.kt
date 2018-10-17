package com.example.yom.exchangeapp.dto

data class ExchangeDTO(val code: String, val moneyType: String, val valueSelling: String, val valueBuying: String, val flag: String, var isFollow: Boolean = false)