package com.nbstocks.nbstocks.domain.model

data class DailyStockDomainModel(
    val timestamp: String?,
    val open: String?,
    val high: String?,
    val low: String?,
    val close: String?,
    val volume: String?
)
