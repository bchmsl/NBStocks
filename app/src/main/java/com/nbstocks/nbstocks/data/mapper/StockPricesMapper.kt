package com.nbstocks.nbstocks.data.mapper

import com.nbstocks.nbstocks.data.remote.model.StockPricesDto
import com.nbstocks.nbstocks.domain.model.StockPricesDomainModel

fun List<StockPricesDto>.toStockPricesDomainModelList() = map { it.toStockPricesDomainModel() }

fun StockPricesDto.toStockPricesDomainModel() = StockPricesDomainModel(
    timestamp = timestamp,
    open = open,
    high = high,
    low = low,
    close = close,
    volume = volume
)