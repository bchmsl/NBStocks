package com.nbstocks.nbstocks.data.remote.model

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

data class DailyStocksDto(
    @Json(name = "Time Series (Daily)")
    val timeSeries: Map<String, StockPrice>
) {
    @JsonClass(generateAdapter = true)
    data class StockPrice(
        @Json(name = "1. open")
        val `open`: String,
        @Json(name = "2. high")
        val high: String,
        @Json(name = "3. low")
        val low: String,
        @Json(name = "4. close")
        val close: String,
        @Json(name = "5. volume")
        val volume: String
    )
}