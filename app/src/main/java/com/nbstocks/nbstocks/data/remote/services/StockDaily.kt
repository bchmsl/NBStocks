package com.nbstocks.nbstocks.data.remote.services

import com.nbstocks.nbstocks.common.constants.RapidApiParams
import okhttp3.ResponseBody
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Query

interface StockDaily {
    companion object {
        val BASE_URL = RapidApiParams.getRapidApiBaseUrl(RapidApiParams.ALPHA_VANTAGE_RAPID_API_HOST)
        val API_KEY = RapidApiParams.getRapidApiKey()
    }

    @GET("query")
    suspend fun getStocksDetails(
        @Query("function") function: String = "TIME_SERIES_DAILY",
        @Query("symbol") symbol: String,
        @Query("datatype") dataType: String = "csv",
        @Header("X-RapidAPI-Key") apiKey: String = API_KEY,
    ): ResponseBody
}