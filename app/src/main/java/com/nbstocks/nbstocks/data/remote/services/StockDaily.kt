package com.nbstocks.nbstocks.data.remote.services

import com.nbstocks.nbstocks.BuildConfig
import com.nbstocks.nbstocks.data.remote.model.DailyStocksDto

import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Query

interface StockDaily {

    companion object {
        const val API_KEY = BuildConfig.API_KEY_STOCK_DETAILS
        const val BASE_URL = "https://www.alphavantage.co/"
    }

    @GET("stocks")
    suspend fun getStocksDetails(
        @Query("symbol") symbol: String,
        @Query("function") function: String = "TIME_SERIES_DAILY",
        @Header("apikey") rapidApiKey: String = API_KEY
    ): Response<DailyStocksDto>

}