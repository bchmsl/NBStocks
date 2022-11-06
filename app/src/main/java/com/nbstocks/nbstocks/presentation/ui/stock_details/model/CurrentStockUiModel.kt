package com.nbstocks.nbstocks.presentation.ui.stock_details.model

data class CurrentStockUiModel(
    val currentPrice: DataShort?,
    val earningsGrowth: DataShort?,
    val financialCurrency: String?,
    val freeCashflow: DataLong?,
    val grossMargins: DataShort?,
    val grossProfits: DataLong?,
    val maxAge: Int?,
    val operatingCashflow: DataLong?,
    val operatingMargins: DataShort?,
    val profitMargins: DataShort?,
    val recommendationKey: String?,
    val recommendationMean: DataShort?,
    val revenueGrowth: DataShort?,
    val revenuePerShare: DataShort?,
    val targetHighPrice: DataShort?,
    val targetLowPrice: DataShort?,
    val targetMeanPrice: DataShort?,
    val targetMedianPrice: DataShort?,
    val totalCash: DataLong?,
    val totalDebt: DataLong?,
    val totalRevenue: DataLong?,
    val symbol: String
) {
    data class DataShort(
        val raw: Double?,
        val fmt: String?
    )

    data class DataLong(
        val fmt: String?,
        val longFmt: String?,
        val raw: Long?
    )
}