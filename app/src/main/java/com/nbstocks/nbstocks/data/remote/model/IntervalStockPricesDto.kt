package com.nbstocks.nbstocks.data.remote.model



data class IntervalStockPricesDto(
    val chart: Chart?
) {
    data class Chart(
        val error: Any?,
        val result: List<Result?>?
    ) {
        data class Result(
            val indicators: Indicators?,
            val meta: Meta?,
            val timestamp: List<Long?>?
        ) {
            data class Indicators(
                val quote: List<Quote?>?
            ) {

                data class Quote(
                    val close: List<Double?>?,
                    val high: List<Double?>?,
                    val low: List<Double?>?,
                    val `open`: List<Double?>?,
                    val volume: List<Long?>?
                )
            }
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
    }
}