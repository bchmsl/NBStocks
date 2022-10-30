package com.nbstocks.nbstocks.presentation.ui.stock_details

import com.anychart.chart.common.dataentry.DataEntry
import com.anychart.chart.common.dataentry.ValueDataEntry
import com.nbstocks.nbstocks.common.custom_views.CustomWaterfallChart
import com.nbstocks.nbstocks.common.extensions.toMonthDay
import com.nbstocks.nbstocks.presentation.ui.stock_details.model.StockPricesUiModel
import com.anychart.core.waterfall.series.Waterfall

class StockPricesChart : CustomWaterfallChart<StockPricesUiModel>() {
    var backgroundColor = ""
    override fun mapData(list: List<StockPricesUiModel>): List<DataEntry> {
        val data = mutableListOf<DataEntry>()
        for (i in list) {
            if (data.size < 14) {
                data.add(
                    ValueDataEntry(
                        i.timestamp?.toMonthDay(),
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