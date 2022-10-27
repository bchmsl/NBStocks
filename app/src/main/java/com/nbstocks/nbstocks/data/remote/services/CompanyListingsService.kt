package com.nbstocks.nbstocks.data.remote.services

import com.nbstocks.nbstocks.common.constants.RapidApiParams
import com.nbstocks.nbstocks.data.remote.model.CompanyListingResponseDto
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Query

interface CompanyListingsService {
    companion object {
        val BASE_URL = RapidApiParams.getRapidApiBaseUrl(RapidApiParams.TWELVE_DATA_RAPID_API_HOST)
        val API_KEY = RapidApiParams.getRapidApiKey()
    }

    @GET("stocks")
    suspend fun getListings(
        @Query("exchange") exchange: String = "NASDAQ",
        @Header("X-RapidAPI-Key") rapidApiKey: String = API_KEY
    ): Response<CompanyListingResponseDto>
}