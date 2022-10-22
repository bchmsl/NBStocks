package com.nbstocks.nbstocks.common.mapper

import com.nbstocks.nbstocks.common.extensions.toStockType
import com.nbstocks.nbstocks.data.local.model.CompanyListingEntity
import com.nbstocks.nbstocks.data.remote.model.CompanyListingResponseDto
import com.nbstocks.nbstocks.domain.model.CompanyListingDomainModel
import com.nbstocks.nbstocks.presentation.stocks.model.CompanyListingUiModel

fun CompanyListingEntity.toCompanyListingDomainModel() =
    CompanyListingDomainModel(
        symbol = symbol,
        name = name,
        exchange = exchange,
        currency = currency,
        country = country,
        type = type?.toStockType()
    )

fun CompanyListingDomainModel.toCompanyListingEntity() =
    CompanyListingEntity(
        symbol = symbol,
        name = name,
        exchange = exchange,
        currency = currency,
        country = country,
        type = type?.typeName
    )

fun CompanyListingResponseDto.CompanyListingDto.toCompanyListingDomainModel() =
    CompanyListingDomainModel(
        symbol = symbol,
        name = name,
        exchange = exchange,
        currency = currency,
        country = country,
        type = type?.toStockType()
    )

fun CompanyListingDomainModel.toCompanyListingUiModel() =
    CompanyListingUiModel(
        symbol = symbol,
        name = name,
        exchange = exchange,
        currency = currency,
        country = country,
        type = type
    )