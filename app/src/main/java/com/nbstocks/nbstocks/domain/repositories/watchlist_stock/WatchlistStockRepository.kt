package com.nbstocks.nbstocks.domain.repositories.watchlist_stock

import com.nbstocks.nbstocks.common.handlers.Resource
import com.nbstocks.nbstocks.data.remote.model.WatchlistStockInfoDto
import com.nbstocks.nbstocks.domain.model.CurrentStockDomainModel
import com.nbstocks.nbstocks.domain.model.WatchlistStockInfoDomainModel
import kotlinx.coroutines.flow.Flow

interface WatchlistStockRepository {
    suspend fun addWatchlistStock(symbol: String)
    suspend fun getWatchlistItems()
    suspend fun getWatchlistStocksInformation(symbols: String) : Flow<Resource<WatchlistStockInfoDomainModel>>
    suspend fun removeWatchlistStock(symbol: String)
}