package com.nbstocks.nbstocks.domain.model

import com.google.gson.annotations.SerializedName

data class CurrentStockDomainModel(
    val symbol: String = "",
    val `open`: String = "",
    val high: String = "",
    val low: String = "",
    val price: String = "",
    val volume: String = "",
    val latestTradingDay: String = "",
    val previousClose: String = "",
    val change: String = "",
    val changePercent: String = ""
)
