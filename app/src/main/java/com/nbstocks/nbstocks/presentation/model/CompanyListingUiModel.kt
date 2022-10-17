package com.nbstocks.nbstocks.presentation.model

import com.nbstocks.nbstocks.common.stocktype.StockType

data class CompanyListingUiModel(
    val symbol: String?,
    val name: String?,
    val exchange: String?,
    val currency: String?,
    val country: String?,
    val type: StockType?
)
