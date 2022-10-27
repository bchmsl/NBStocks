package com.nbstocks.nbstocks.data.remote.model

data class StockPricesDto(
    val timestamp: String?,
    val open: String?,
    val high: String?,
    val low: String?,
    val close: String?,
    val volume: String?
)