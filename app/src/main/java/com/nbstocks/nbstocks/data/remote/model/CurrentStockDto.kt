package com.nbstocks.nbstocks.data.remote.model


import com.google.gson.annotations.SerializedName

data class CurrentStockDto(
    val symbol: String,
    val open: String,
    val high: String,
    val low: String,
    val price: String,
    val volume: String,
    val latestDay: String,
    val previousClose: String,
    val change: String,
    val changePercent: String
)