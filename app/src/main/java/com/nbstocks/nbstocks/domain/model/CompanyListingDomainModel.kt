package com.nbstocks.nbstocks.domain.model

import com.nbstocks.nbstocks.common.stocktype.StockType


data class CompanyListingDomainModel(
    val symbol: String?,
    val name: String?,
    val exchange: String?,
    val currency: String?,
    val country: String?,
    val type: StockType?
)