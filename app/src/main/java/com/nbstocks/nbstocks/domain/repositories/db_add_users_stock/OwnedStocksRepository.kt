package com.nbstocks.nbstocks.domain.repositories.db_add_users_stock

import com.nbstocks.nbstocks.domain.model.UsersStockDomainModel

interface OwnedStocksRepository {
    suspend fun buyStocks(usersStockDomainModel: UsersStockDomainModel)
    suspend fun getOwnedStocks()
    suspend fun sellOwnedStocks(symbol: String)
}