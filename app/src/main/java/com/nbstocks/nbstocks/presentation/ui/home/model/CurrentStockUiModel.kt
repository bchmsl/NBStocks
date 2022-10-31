package com.nbstocks.nbstocks.presentation.ui.home.model

data class CurrentStockUiModel(
    val symbol: String,
    val `open`: String,
    val high: String,
    val low: String,
    val price: String,
    val volume: String,
    val latestTradingDay: String,
    val previousClose: String,
    val change: String,
    val changePercent: String
)
