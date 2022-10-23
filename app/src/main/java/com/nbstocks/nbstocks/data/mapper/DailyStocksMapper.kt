package com.nbstocks.nbstocks.data.mapper

import com.nbstocks.nbstocks.data.remote.model.DailyStockDto
import com.nbstocks.nbstocks.domain.model.DailyStockDomainModel

fun List<DailyStockDto>.toDailyStockDomainModelList() = map { it.toDailyStockDomainModel() }

fun DailyStockDto.toDailyStockDomainModel() = DailyStockDomainModel(
    timestamp = timestamp,
    open = open,
    high = high,
    low = low,
    close = close,
    volume = volume
)