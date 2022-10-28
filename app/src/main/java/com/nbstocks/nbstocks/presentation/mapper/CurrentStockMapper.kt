package com.nbstocks.nbstocks.presentation.mapper

import com.nbstocks.nbstocks.domain.model.CurrentStockDomainModel
import com.nbstocks.nbstocks.domain.model.StockPricesDomainModel
import com.nbstocks.nbstocks.presentation.ui.stock_details.model.CurrentStockUiModel
import com.nbstocks.nbstocks.presentation.ui.stock_details.model.StockPricesUiModel

fun CurrentStockUiModel.toCurrentStockDomain() = CurrentStockDomainModel(
    symbol = symbol,
    open = open,
    high = high,
    low = low,
    price = price,
    volume = volume,
    latestTradingDay = latestTradingDay,
    previousClose = previousClose,
    change = change,
    changePercent = changePercent
)
