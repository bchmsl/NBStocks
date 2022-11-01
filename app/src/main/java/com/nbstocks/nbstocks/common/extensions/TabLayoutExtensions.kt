package com.nbstocks.nbstocks.common.extensions

import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayout.Tab
import com.nbstocks.nbstocks.common.constants.ApiServiceHelpers.YahooFinanceService.ServiceTimestamps


fun TabLayout.onTabSelected(function: () -> Unit) {
    this.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
        override fun onTabSelected(tab: Tab) {
            function()
        }

        override fun onTabUnselected(tab: Tab) {}
        override fun onTabReselected(tab: Tab) {}
    })
}

fun TabLayout.doSelectedTask(function: (range: String, interval: String) -> Unit) {
    if (this.selectedTabPosition == 0) {
        function(ServiceTimestamps.MONTH1, ServiceTimestamps.DAY1)
    }else if(this.selectedTabPosition == 1){
        function(ServiceTimestamps.YEAR1, ServiceTimestamps.MONTH1)

    }
}