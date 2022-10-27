package com.nbstocks.nbstocks.data.mapper

import com.nbstocks.nbstocks.common.extensions.toStockType
import com.nbstocks.nbstocks.data.local.model.CompanyListingEntity
import com.nbstocks.nbstocks.data.remote.model.CompanyListingResponseDto
import com.nbstocks.nbstocks.data.remote.model.CurrentStockDto
import com.nbstocks.nbstocks.domain.model.CompanyListingDomainModel
import com.nbstocks.nbstocks.domain.model.CurrentStockDomainModel
import com.nbstocks.nbstocks.presentation.ui.stock_details.model.CurrentStockUiModel


fun CurrentStockDto.toCurrentStockDomainModel() = CurrentStockDomainModel(
    symbol, open,
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
    symbol, open,
    high = high,
    low = low,
    price = price,
    volume = volume,
    latestTradingDay = latestTradingDay,
    previousClose = previousClose,
    change = change,
    changePercent = changePercent
)