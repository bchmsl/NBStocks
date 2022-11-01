package com.nbstocks.nbstocks.presentation.ui.common.model

data class WatchlistStockInfoUiModel(
    val data: List<DataItem>
) {
    data class DataItem(
        val displayName: String?,
        val currency: String?,
        val financialCurrency: String?,
        val fullExchangeName: String?,
        val regularMarketChangePercent: Double?,
        val regularMarketDayHigh: Double?,
        val regularMarketDayLow: Double?,
        val regularMarketPrice: Double?,
        val shortName: String?,
        val symbol: String?,
        var owned: Boolean? = false,
        var ownedAmount: Double? = null,
        var ownedPrice: String? = null
    )
}