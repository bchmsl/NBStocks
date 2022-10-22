package com.nbstocks.nbstocks.domain.repositories.stock_details

import com.nbstocks.nbstocks.common.Resource
import com.nbstocks.nbstocks.data.remote.model.DailyStockDto
import com.nbstocks.nbstocks.domain.model.DailyStockDomainModel
import kotlinx.coroutines.flow.Flow

interface StockDetailsRepository {
    suspend fun getStocksDetails(symbol:String):Flow<Resource<List<DailyStockDomainModel>>>
}