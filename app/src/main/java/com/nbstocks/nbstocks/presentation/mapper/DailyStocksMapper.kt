package com.nbstocks.nbstocks.presentation.mapper

import com.nbstocks.nbstocks.domain.model.StockPricesDomainModel
import com.nbstocks.nbstocks.presentation.ui.stock_details.model.StockPricesUiModel


fun List<StockPricesDomainModel>.toStockPricesModelList() = map { it.toStockPricesUiModel() }

fun StockPricesDomainModel.toStockPricesUiModel() = StockPricesUiModel(
    timestamp = timestamp,
    open = open,
    high = high,
    low = low,
    close = close,
    volume = volume
)

