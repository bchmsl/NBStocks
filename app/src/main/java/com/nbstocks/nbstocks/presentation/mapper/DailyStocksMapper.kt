package com.nbstocks.nbstocks.presentation.mapper

import com.nbstocks.nbstocks.domain.model.DailyStockDomainModel
import com.nbstocks.nbstocks.presentation.ui.stock_details.model.DailyStockUiModel


fun List<DailyStockDomainModel>.toDailyStockUiModelList() = map { it.toDailyStockUiModel() }

fun DailyStockDomainModel.toDailyStockUiModel() = DailyStockUiModel(
    timestamp = timestamp,
    open = open,
    high = high,
    low = low,
    close = close,
    volume = volume
)

