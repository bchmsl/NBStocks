package com.nbstocks.nbstocks.domain.repositories.get_stock_amount

import com.nbstocks.nbstocks.domain.model.UsersStockDomainModel

interface GetStockAmountRepository {
    suspend fun getStockAmount(symbol: String)
}