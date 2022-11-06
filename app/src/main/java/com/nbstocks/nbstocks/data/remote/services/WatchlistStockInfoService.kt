package com.nbstocks.nbstocks.data.remote.services

import com.nbstocks.nbstocks.common.constants.ApiServiceHelpers.YahooFinanceService
import com.nbstocks.nbstocks.data.remote.model.WatchlistStockInfoDto
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface WatchlistStockInfoService {
    companion object{
        const val PATH = YahooFinanceService.PATH_QUOTE
    }
    @GET(PATH)
    suspend fun getWatchlistStockInfo(
        @Query("symbols") symbols: String,
    ): Response<WatchlistStockInfoDto>
}