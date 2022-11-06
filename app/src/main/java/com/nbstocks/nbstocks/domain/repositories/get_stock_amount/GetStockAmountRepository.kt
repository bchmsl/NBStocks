package com.nbstocks.nbstocks.domain.repositories.get_stock_amount

interface GetStockAmountRepository {
    suspend fun getStockAmount(symbol: String)
}