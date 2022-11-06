package com.nbstocks.nbstocks.presentation.mapper

import com.nbstocks.nbstocks.domain.model.CurrentStockDomainModel
import com.nbstocks.nbstocks.presentation.ui.stock_details.model.CurrentStockUiModel

fun CurrentStockDomainModel.toCurrentStockUiModel(symbol: String) = CurrentStockUiModel(
    currentPrice = currentPrice?.toDataShortUi(),
    earningsGrowth = earningsGrowth?.toDataShortUi(),
    financialCurrency = financialCurrency,
    freeCashflow = freeCashflow?.toDataLongUi(),
    grossMargins = grossMargins?.toDataShortUi(),
    grossProfits = grossProfits?.toDataLongUi(),
    maxAge = maxAge,
    operatingCashflow = operatingCashflow?.toDataLongUi(),
    operatingMargins = operatingMargins?.toDataShortUi(),
    profitMargins = profitMargins?.toDataShortUi(),
    recommendationKey = recommendationKey,
    recommendationMean = recommendationMean?.toDataShortUi(),
    revenueGrowth = revenueGrowth?.toDataShortUi(),
    revenuePerShare = revenuePerShare?.toDataShortUi(),
    targetHighPrice = targetHighPrice?.toDataShortUi(),
    targetLowPrice = targetLowPrice?.toDataShortUi(),
    targetMeanPrice = targetMeanPrice?.toDataShortUi(),
    targetMedianPrice = targetMedianPrice?.toDataShortUi(),
    totalCash = totalCash?.toDataLongUi(),
    totalDebt = totalDebt?.toDataLongUi(),
    totalRevenue = totalRevenue?.toDataLongUi(),
    symbol = symbol
)


fun CurrentStockDomainModel.DataShort.toDataShortUi() =
    CurrentStockUiModel.DataShort(
        raw = raw,
        fmt = fmt
    )

fun CurrentStockDomainModel.DataLong.toDataLongUi() =
    CurrentStockUiModel.DataLong(
        raw = raw,
        fmt = fmt,
        longFmt = longFmt
    )