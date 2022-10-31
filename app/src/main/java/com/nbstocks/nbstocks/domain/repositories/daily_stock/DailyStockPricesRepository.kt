package com.nbstocks.nbstocks.domain.repositories.daily_stock

import com.nbstocks.nbstocks.common.handlers.Resource
import com.nbstocks.nbstocks.domain.model.IntervalStockPricesDomainModel
import com.nbstocks.nbstocks.domain.model.StockPricesDomainModel
import kotlinx.coroutines.flow.Flow

interface DailyStockPricesRepository {
    suspend fun getStocksDetails(symbol:String, range: String, interval: String):Flow<Resource<IntervalStockPricesDomainModel?>>
}