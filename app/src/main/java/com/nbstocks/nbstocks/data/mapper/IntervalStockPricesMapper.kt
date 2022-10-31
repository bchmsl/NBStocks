package com.nbstocks.nbstocks.data.mapper

import com.nbstocks.nbstocks.data.remote.model.IntervalStockPricesDto
import com.nbstocks.nbstocks.domain.model.IntervalStockPricesDomainModel

fun IntervalStockPricesDto.toIntervalStockPricesDomainModel() =
    chart?.result?.get(0)?.timestamp?.mapIndexed { index, timestamp ->
        IntervalStockPricesDomainModel.DailyData(
            timestamp = timestamp,
            open = chart.result[0]?.indicators?.quote?.get(0)?.open?.get(index),
            high = chart.result[0]?.indicators?.quote?.get(0)?.high?.get(index),
            low = chart.result[0]?.indicators?.quote?.get(0)?.low?.get(index),
            close = chart.result[0]?.indicators?.quote?.get(0)?.close?.get(index),
            volume = chart.result[0]?.indicators?.quote?.get(0)?.volume?.get(index)
        )
    }?.let {
        IntervalStockPricesDomainModel(
            meta = chart.result[0]?.meta?.toMetaDomainModel(),
            data = it
        )
    }

fun IntervalStockPricesDto.Chart.Result.Meta.toMetaDomainModel() =
    IntervalStockPricesDomainModel.Meta(
        currency = currency,
        currentTradingPeriod = currentTradingPeriod?.toCurrentTradingPeriodDomainModel(),
        dataGranularity = dataGranularity,
        exchangeTimezoneName = exchangeTimezoneName,
        instrumentType = instrumentType,
        range = range,
        regularMarketPrice = regularMarketPrice,
        symbol = symbol,
        timezone = timezone
    )

fun IntervalStockPricesDto.Chart.Result.Meta.CurrentTradingPeriod.toCurrentTradingPeriodDomainModel() =
    IntervalStockPricesDomainModel.Meta.CurrentTradingPeriod(
        regular = regular?.toRegularDomainModel()
    )

fun IntervalStockPricesDto.Chart.Result.Meta.CurrentTradingPeriod.Regular.toRegularDomainModel() =
    IntervalStockPricesDomainModel.Meta.CurrentTradingPeriod.Regular(
        end = end,
        gmtoffset = gmtoffset,
        start = start,
        timezone = timezone
    )