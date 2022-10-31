package com.nbstocks.nbstocks.data.remote.services

import com.nbstocks.nbstocks.common.constants.ApiServiceHelpers.YahooFinanceService
import com.nbstocks.nbstocks.data.remote.model.IntervalStockPricesDto
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface IntervalStockPricesService {
    companion object{
        const val PATH = YahooFinanceService.PATH_CHART
        val TIMESTAMP = YahooFinanceService.ServiceTimestamps
    }
    @GET(PATH)
    suspend fun getIntervalStockPrices(
        @Path("symbol") symbol: String,
        @Query("range") range: String,
        @Query("interval") interval: String,
        @Query("metrics") metrics: String
        = YahooFinanceService.ServiceMetrics.HIGH
    ): Response<IntervalStockPricesDto>

}

