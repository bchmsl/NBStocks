package com.nbstocks.nbstocks.data.remote.services

import com.nbstocks.nbstocks.BuildConfig
import okhttp3.ResponseBody
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Query

interface StockDaily {

    companion object {
        const val API_KEY = BuildConfig.API_KEY_STOCK_DETAILS
        const val BASE_URL = "https://www.alphavantage.co/"
    }

    @GET("query")
    suspend fun getStocksDetails(
        @Query("symbol") symbol: String,
        @Query("function") function: String = "TIME_SERIES_DAILY",
        @Query("apikey") apikey: String = "AE2SP6X1JDFC2150",
        @Query("datatype") datatype: String = "csv"
    ): ResponseBody

}