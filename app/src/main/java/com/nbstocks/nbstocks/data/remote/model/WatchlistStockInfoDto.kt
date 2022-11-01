package com.nbstocks.nbstocks.data.remote.model


data class WatchlistStockInfoDto(
    val quoteResponse: QuoteResponse?
) {
    data class QuoteResponse(
        val error: Any?,
        val result: List<Result?>?
    ) {
        data class Result(
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
        )
    }
}