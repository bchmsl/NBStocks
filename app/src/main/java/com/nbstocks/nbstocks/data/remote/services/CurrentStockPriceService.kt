package com.nbstocks.nbstocks.data.remote.services

import com.nbstocks.nbstocks.common.constants.ApiServiceHelpers.YahooFinanceService
import com.nbstocks.nbstocks.data.remote.model.CurrentStockDto
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface CurrentStockPriceService {
    companion object{
        const val PATH = YahooFinanceService.PATH_QUOTE_SUMMARY
        const val MODULE = YahooFinanceService.ServiceFunctions.FINANCIAL_DATA
    }

    @GET(PATH)
    suspend fun getCurrentStockPrice(
        @Path("symbol") symbol: String,
        @Query("modules") modules: String = MODULE
    ): Response<CurrentStockDto>

}