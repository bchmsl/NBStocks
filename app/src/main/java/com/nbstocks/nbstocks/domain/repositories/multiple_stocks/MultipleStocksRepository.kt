package com.nbstocks.nbstocks.domain.repositories.multiple_stocks

import com.nbstocks.nbstocks.common.handlers.Resource
import com.nbstocks.nbstocks.domain.model.WatchlistStockInfoDomainModel
import kotlinx.coroutines.flow.Flow

interface MultipleStocksRepository {
    suspend fun getWatchlistStocksInformation(symbols: String)
            : Flow<Resource<WatchlistStockInfoDomainModel>>
}