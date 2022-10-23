package com.nbstocks.nbstocks.presentation.mapper

import com.nbstocks.nbstocks.domain.model.CompanyListingDomainModel
import com.nbstocks.nbstocks.presentation.ui.company_listings.model.CompanyListingUiModel

fun CompanyListingDomainModel.toCompanyListingUiModel() =
    CompanyListingUiModel(
        symbol = symbol,
        name = name,
        exchange = exchange,
        currency = currency,
        country = country,
        type = type
    )