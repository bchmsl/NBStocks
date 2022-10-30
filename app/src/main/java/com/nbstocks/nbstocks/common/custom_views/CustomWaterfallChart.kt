package com.nbstocks.nbstocks.common.custom_views

import com.anychart.AnyChart
import com.anychart.chart.common.dataentry.DataEntry
import com.anychart.data.Set
import com.anychart.core.waterfall.series.Waterfall

abstract class CustomWaterfallChart<T> {

    private var chartData = listOf<T>()
    val set: Set by lazy { Set.instantiate() }
    val waterfall: com.anychart.charts.Waterfall by lazy { AnyChart.waterfall() }

    fun initChart(){
        onCreateChart()
    }

    open fun onCreateChart() {
        waterfall.yScale().minimum(0.0)
        waterfall.labels().enabled(false)
        val series = waterfall.waterfall(set, "")
        onChartCreated(series)
    }
    private fun setDataToChart(set: Set) {
        set.data(mapData(chartData))
    }

    abstract fun onChartCreated(waterfall: Waterfall)

    fun submitData(list: List<T>) {
        chartData = list
        setDataToChart(set)
    }

    abstract fun mapData(list: List<T>): List<DataEntry>
}