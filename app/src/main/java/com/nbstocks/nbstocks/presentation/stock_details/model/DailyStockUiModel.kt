package com.nbstocks.nbstocks.presentation.stock_details.model

data class DailyStockUiModel(
    val timestamp: String?,
    val open: String?,
    val high: String?,
    val low: String?,
    val close: String?,
    val volume: String?
)
