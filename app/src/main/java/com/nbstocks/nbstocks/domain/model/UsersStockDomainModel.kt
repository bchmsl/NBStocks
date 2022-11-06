package com.nbstocks.nbstocks.domain.model

data class UsersStockDomainModel(
    val symbol: String = "",
    val price: String = "",
    val amountInStocks: Double? = 0.0
)
