package com.xchyom.yom.exchangeapp.dto

import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import com.fasterxml.jackson.annotation.JsonProperty

@JsonIgnoreProperties(ignoreUnknown = true)
data class VideosDTO(
        @JsonProperty("baslik") val viodeTitle: String,
        @JsonProperty("link") val videoLink: String,
        @JsonProperty("resim") val videoPic: String
)
