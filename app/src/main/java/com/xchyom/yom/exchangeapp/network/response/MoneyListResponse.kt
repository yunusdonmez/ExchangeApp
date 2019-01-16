package com.xchyom.yom.exchangeapp.network.response

import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import com.fasterxml.jackson.annotation.JsonProperty

@JsonIgnoreProperties(ignoreUnknown = true)
class MoneyListResponse(@JsonProperty("code") val code: String,
                        @JsonProperty("name") val moneyType: String,
                        @JsonProperty("buy") val valueBuying: String,
                        @JsonProperty("sell") val valueSelling: String
)