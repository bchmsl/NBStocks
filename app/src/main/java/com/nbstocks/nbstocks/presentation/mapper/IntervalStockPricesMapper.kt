package com.nbstocks.nbstocks.presentation.mapper

import com.nbstocks.nbstocks.domain.model.IntervalStockPricesDomainModel
import com.nbstocks.nbstocks.presentation.ui.stock_details.model.IntervalStockPricesUiModel

fun IntervalStockPricesDomainModel.toIntervalStockPricesUiModel() =
    IntervalStockPricesUiModel(
        meta = meta?.toMetaUiModel(),
        data = data.map { it.toDailyDataUiModel() }
    )

fun IntervalStockPricesDomainModel.DailyData.toDailyDataUiModel() =
    IntervalStockPricesUiModel.DailyData(
        timestamp = timestamp,
        open = open,
        high = high,
        low = low,
        close = close,
        volume = volume
    )

fun IntervalStockPricesDomainModel.Meta.toMetaUiModel() =
    IntervalStockPricesUiModel.Meta(
        currency = currency,
        currentTradingPeriod = currentTradingPeriod?.toCurrentTradingPeriodUiModel(),
        dataGranularity = dataGranularity,
        exchangeTimezoneName = exchangeTimezoneName,
        instrumentType = instrumentType,
        range = range,
        regularMarketPrice = regularMarketPrice,
        symbol = symbol,
        timezone = timezone
    )

fun IntervalStockPricesDomainModel.Meta.CurrentTradingPeriod.toCurrentTradingPeriodUiModel() =
    IntervalStockPricesUiModel.Meta.CurrentTradingPeriod(
        regular = regular?.toRegularUiModel()
    )

fun IntervalStockPricesDomainModel.Meta.CurrentTradingPeriod.Regular.toRegularUiModel() =
    IntervalStockPricesUiModel.Meta.CurrentTradingPeriod.Regular(
        end = end,
        gmtoffset = gmtoffset,
        start = start,
        timezone = timezone
    )