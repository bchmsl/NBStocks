package com.nbstocks.nbstocks.presentation.ui.stock_details

import com.anychart.chart.common.dataentry.DataEntry
import com.anychart.chart.common.dataentry.ValueDataEntry
import com.nbstocks.nbstocks.common.custom_views.CustomWaterfallChart
import com.nbstocks.nbstocks.common.extensions.toMonthDay
import com.nbstocks.nbstocks.presentation.ui.stock_details.model.StockPricesUiModel
import com.anychart.core.waterfall.series.Waterfall
import com.nbstocks.nbstocks.common.extensions.toDate
import com.nbstocks.nbstocks.presentation.ui.stock_details.model.IntervalStockPricesUiModel

class StockPricesChart : CustomWaterfallChart<IntervalStockPricesUiModel.DailyData>() {
    var backgroundColor = ""
    override fun mapData(list: List<IntervalStockPricesUiModel.DailyData>): List<DataEntry> {
        val data = mutableListOf<DataEntry>()
        for (i in list) {
            if (data.size < 14) {
                data.add(
                    ValueDataEntry(
                        i.timestamp?.toDate(),
                        ((i.close)!!.toDouble() - (i.open)!!.toDouble())
                    )
                )
            } else {
                break
            }
        }
        return data
    }

    override fun onChartCreated(waterfall: Waterfall) {
        waterfall.normal().fallingFill("#FB3E64", 1.0)
        waterfall.normal().fallingStroke("#FB3E64", 1, "null", "round", "null")
        waterfall.normal().risingFill("#5DE066", 1.0)
        waterfall.normal().risingStroke("#5DE066", 1, "null", "round", "null")
        this.waterfall.background().enabled(true).fill(backgroundColor)
    }
}