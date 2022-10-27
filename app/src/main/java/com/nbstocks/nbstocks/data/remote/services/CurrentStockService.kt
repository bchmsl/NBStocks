package com.nbstocks.nbstocks.data.remote.services

import com.nbstocks.nbstocks.common.constants.RapidApiParams
import com.nbstocks.nbstocks.data.remote.model.CurrentStockDto
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Query


interface CurrentStockService {
    companion object {
        val BASE_URL = RapidApiParams.getRapidApiBaseUrl(RapidApiParams.ALPHA_VANTAGE_RAPID_API_HOST)
        val API_KEY = RapidApiParams.getRapidApiKey()
    }

    @GET("query")
    suspend fun getCurrentStock(
        @Query("function") function: String = "GLOBAL_QUOTE",
        @Query("symbol") symbol: String,
        @Header("X-RapidAPI-Key") apiKey: String = API_KEY,
    ): Response<CurrentStockDto>
}