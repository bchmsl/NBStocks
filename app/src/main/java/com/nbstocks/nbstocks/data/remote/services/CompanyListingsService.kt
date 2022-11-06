package com.nbstocks.nbstocks.data.remote.services

import com.nbstocks.nbstocks.common.constants.ApiServiceHelpers.RapidApiService
import com.nbstocks.nbstocks.data.remote.model.CompanyListingResponseDto
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Query

interface CompanyListingsService {
    companion object {
        const val BASE_URL = RapidApiService.BASE_URL
        const val PATH = RapidApiService.PATH_QUOTE
        const val API_KEY = RapidApiService.RAPID_API_KEY
    }

    @GET(PATH)
    suspend fun getListings(
        @Query("exchange") exchange: String = "NASDAQ",
        @Header("X-RapidAPI-Key") rapidApiKey: String = API_KEY
    ): Response<CompanyListingResponseDto>
}