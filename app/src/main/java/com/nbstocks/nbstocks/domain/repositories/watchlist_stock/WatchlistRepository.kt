package com.nbstocks.nbstocks.domain.repositories.watchlist_stock

interface WatchlistRepository {
    suspend fun addWatchlistStock(symbol: String)
    suspend fun getWatchlistItems()
    suspend fun removeWatchlistStock(symbol: String)
}