package com.nbstocks.nbstocks.data.mapper

import com.nbstocks.nbstocks.data.remote.model.WatchlistStockInfoDto
import com.nbstocks.nbstocks.domain.model.WatchlistStockInfoDomainModel

fun WatchlistStockInfoDto.toWatchlistStockInfoDomainModel() = WatchlistStockInfoDomainModel(
    data = this.quoteResponse?.result?.map { result ->
        WatchlistStockInfoDomainModel.DataItem(
            displayName = result?.displayName,
            currency = result?.currency,
            financialCurrency = result?.financialCurrency,
            fullExchangeName = result?.fullExchangeName,
            regularMarketChangePercent = result?.regularMarketChangePercent,
            regularMarketDayHigh = result?.regularMarketDayHigh,
            regularMarketDayLow = result?.regularMarketDayLow,
            regularMarketPrice = result?.regularMarketPrice,
            shortName = result?.shortName,
            symbol = result?.symbol,
        )
    } ?: emptyList()
)