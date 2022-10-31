package com.nbstocks.nbstocks.presentation.ui.stock_details.model

data class IntervalStockPricesUiModel(
    val meta: Meta?,
    val data: List<DailyData>
) {
    data class DailyData(
        val timestamp: Long?,
        val open: Double?,
        val high: Double?,
        val low: Double?,
        val close: Double?,
        val volume: Int?
    )
    data class Meta(
        val currency: String?,
        val currentTradingPeriod: CurrentTradingPeriod?,
        val dataGranularity: String?,
        val exchangeTimezoneName: String?,
        val instrumentType: String?,
        val range: String?,
        val regularMarketPrice: Double?,
        val symbol: String?,
        val timezone: String?,
    ) {
        data class CurrentTradingPeriod(
            val regular: Regular?
        ) {
            data class Regular(
                val end: Int?,
                val gmtoffset: Int?,
                val start: Int?,
                val timezone: String?
            )
        }
    }
}
