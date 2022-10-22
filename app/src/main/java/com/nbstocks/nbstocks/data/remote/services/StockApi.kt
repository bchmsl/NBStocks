package com.nbstocks.nbstocks.data.remote.services

import com.nbstocks.nbstocks.BuildConfig
import com.nbstocks.nbstocks.data.remote.model.CompanyListingResponseDto
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Query

interface StockApi {
    companion object {
        const val API_KEY = BuildConfig.API_KEY
        const val BASE_URL = "https://twelve-data1.p.rapidapi.com/"
    }

    @GET("stocks")
    suspend fun getListings(
        @Query("exchange") exchange: String = "NASDAQ",
        @Header("X-RapidAPI-Key") rapidApiKey: String = API_KEY
    ): Response<CompanyListingResponseDto>



}