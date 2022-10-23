package com.nbstocks.nbstocks.domain.repositories.daily_stock

import com.nbstocks.nbstocks.common.handlers.Resource
import com.nbstocks.nbstocks.domain.model.DailyStockDomainModel
import kotlinx.coroutines.flow.Flow

interface DailyStockRepository {
    suspend fun getStocksDetails(symbol:String):Flow<Resource<List<DailyStockDomainModel>>>
}