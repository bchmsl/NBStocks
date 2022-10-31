package com.nbstocks.nbstocks.data.mapper

import com.nbstocks.nbstocks.data.remote.model.CurrentStockDto
import com.nbstocks.nbstocks.domain.model.CurrentStockDomainModel
import com.nbstocks.nbstocks.presentation.ui.stock_details.model.CurrentStockUiModel


fun List<CurrentStockDto>.toCurrentStockDomainModelList() = map { it.toCurrentStockDomainModel() }

fun CurrentStockDto.toCurrentStockDomainModel() = CurrentStockDomainModel(
    symbol, open,
    high = high,
    low = low,
    price = price,
    volume = volume,
    latestTradingDay = latestDay,
    previousClose = previousClose,
    change = change,
    changePercent = changePercent
)

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
fun CurrentStockDomainModel.toCurrentStockUiModel() = CurrentStockUiModel(
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