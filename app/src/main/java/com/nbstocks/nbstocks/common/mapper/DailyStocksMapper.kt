package com.nbstocks.nbstocks.common.mapper

import com.nbstocks.nbstocks.data.remote.model.DailyStockDto
import com.nbstocks.nbstocks.domain.model.DailyStockDomainModel
import com.nbstocks.nbstocks.presentation.stock_details.model.DailyStockUiModel

fun List<DailyStockDto>.toDailyStockDomainModelList() = map { it.toDailyStockDomainModel() }

fun List<DailyStockDomainModel>.toDailyStockUiModelList() = map { it.toDailyStockUiModel() }

fun DailyStockDto.toDailyStockDomainModel() = DailyStockDomainModel(
    timestamp = timestamp,
    open = open,
    high = high,
    low = low,
    close = close,
    volume = volume
)

fun DailyStockDomainModel.toDailyStockUiModel() = DailyStockUiModel(
    timestamp = timestamp,
    open = open,
    high = high,
    low = low,
    close = close,
    volume = volume
)

