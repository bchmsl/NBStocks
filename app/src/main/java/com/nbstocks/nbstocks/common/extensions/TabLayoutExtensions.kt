package com.nbstocks.nbstocks.common.extensions

import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayout.Tab
import com.nbstocks.nbstocks.common.constants.StockPricesRequestFunctions


fun TabLayout.onTabSelected(function: () -> Unit) {
    this.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
        override fun onTabSelected(tab: Tab) {
            function()
        }

        override fun onTabUnselected(tab: Tab) {}
        override fun onTabReselected(tab: Tab) {}
    })
}

val TabLayout.currentTab
    get() = run {
        when (this.selectedTabPosition) {
            0 -> StockPricesRequestFunctions.TIME_SERIES_DAILY
            else -> StockPricesRequestFunctions.TIME_SERIES_MONTHLY
        }
    }