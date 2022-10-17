package com.nbstocks.nbstocks.data.remote.model


data class ResponseData(
    val data: List<CompanyListingDto?>?
) {
    data class CompanyListingDto(
        val symbol: String?,
        val name: String?,
        val exchange: String?,
        val currency: String?,
        val country: String?,
        val type: String?
    )
}
