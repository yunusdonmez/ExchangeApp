package com.example.yom.exchangeapp.network.response

import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import com.fasterxml.jackson.annotation.JsonProperty

@JsonIgnoreProperties(ignoreUnknown = true)
class MoneyListResponse(@JsonProperty("full_name") val moneyType: String,
                        @JsonProperty("selling") val valueSelling: String,
                        @JsonProperty("buying") val valueBuying: String,
                        @JsonProperty("code") val code: String)