package com.nbstocks.nbstocks.domain.repositories.watchlist_stock

import com.nbstocks.nbstocks.common.handlers.Resource
import com.nbstocks.nbstocks.domain.model.CurrentStockDomainModel
import kotlinx.coroutines.flow.Flow

interface WatchlistStockRepository {
    suspend fun addWatchlistStock(symbol: String)
    suspend fun getWatchlistStock()
    suspend fun removeWatchlistStock(symbol: String)
}