package com.example.yom.exchangeapp.network.response

import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import com.fasterxml.jackson.annotation.JsonProperty

@JsonIgnoreProperties(ignoreUnknown = true)
class MoneyListResponse(@JsonProperty("name") val moneyType: String,
                        @JsonProperty("sellPrice") val valueSelling: String,
                        @JsonProperty("buyPrice") val valueBuying: String,
                        @JsonProperty("code") val code: String)