package com.nbstocks.nbstocks.data.mapper

import com.nbstocks.nbstocks.data.remote.model.CurrentStockDto
import com.nbstocks.nbstocks.domain.model.CurrentStockDomainModel


fun CurrentStockDto.toCurrentStockDomainModel(symbol: String) = CurrentStockDomainModel(
    currentPrice = quoteSummary?.result?.first()?.financialData?.currentPrice?.toDataShortDomain(),
    earningsGrowth = quoteSummary?.result?.first()?.financialData?.earningsGrowth?.toDataShortDomain(),
    financialCurrency = quoteSummary?.result?.first()?.financialData?.financialCurrency,
    freeCashflow = quoteSummary?.result?.first()?.financialData?.freeCashflow?.toDataLongDomain(),
    grossMargins = quoteSummary?.result?.first()?.financialData?.grossMargins?.toDataShortDomain(),
    grossProfits = quoteSummary?.result?.first()?.financialData?.grossProfits?.toDataLongDomain(),
    maxAge = quoteSummary?.result?.first()?.financialData?.maxAge,
    operatingCashflow = quoteSummary?.result?.first()?.financialData?.operatingCashflow?.toDataLongDomain(),
    operatingMargins = quoteSummary?.result?.first()?.financialData?.operatingMargins?.toDataShortDomain(),
    profitMargins = quoteSummary?.result?.first()?.financialData?.profitMargins?.toDataShortDomain(),
    recommendationKey = quoteSummary?.result?.first()?.financialData?.recommendationKey,
    recommendationMean = quoteSummary?.result?.first()?.financialData?.recommendationMean?.toDataShortDomain(),
    revenueGrowth = quoteSummary?.result?.first()?.financialData?.revenueGrowth?.toDataShortDomain(),
    revenuePerShare = quoteSummary?.result?.first()?.financialData?.revenuePerShare?.toDataShortDomain(),
    targetHighPrice = quoteSummary?.result?.first()?.financialData?.targetHighPrice?.toDataShortDomain(),
    targetLowPrice = quoteSummary?.result?.first()?.financialData?.targetLowPrice?.toDataShortDomain(),
    targetMeanPrice = quoteSummary?.result?.first()?.financialData?.targetMeanPrice?.toDataShortDomain(),
    targetMedianPrice = quoteSummary?.result?.first()?.financialData?.targetMedianPrice?.toDataShortDomain(),
    totalCash = quoteSummary?.result?.first()?.financialData?.totalCash?.toDataLongDomain(),
    totalDebt = quoteSummary?.result?.first()?.financialData?.totalDebt?.toDataLongDomain(),
    totalRevenue = quoteSummary?.result?.first()?.financialData?.totalRevenue?.toDataLongDomain(),
    symbol = symbol
)


fun CurrentStockDto.QuoteSummary.Result.FinancialData.DataShort.toDataShortDomain() =
    CurrentStockDomainModel.DataShort(
        raw = raw,
        fmt = fmt
    )

fun CurrentStockDto.QuoteSummary.Result.FinancialData.DataLong.toDataLongDomain() =
    CurrentStockDomainModel.DataLong(
        raw = raw,
        fmt = fmt,
        longFmt = longFmt
    )