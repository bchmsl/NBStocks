package com.nbstocks.nbstocks.presentation.mapper

import com.nbstocks.nbstocks.domain.model.WatchlistStockInfoDomainModel
import com.nbstocks.nbstocks.presentation.ui.common.model.WatchlistStockInfoUiModel

fun WatchlistStockInfoDomainModel.toWatchListStockUiModel() =
    WatchlistStockInfoUiModel(
        data = this.data.map { dataItem ->
            WatchlistStockInfoUiModel.DataItem(
                displayName = dataItem.displayName,
                currency = dataItem.currency,
                financialCurrency = dataItem.financialCurrency,
                fullExchangeName = dataItem.fullExchangeName,
                regularMarketChangePercent = dataItem.regularMarketChangePercent,
                regularMarketDayHigh = dataItem.regularMarketDayHigh,
                regularMarketDayLow = dataItem.regularMarketDayLow,
                regularMarketPrice = dataItem.regularMarketPrice,
                shortName = dataItem.shortName,
                symbol = dataItem.symbol,
            )
        }
    )