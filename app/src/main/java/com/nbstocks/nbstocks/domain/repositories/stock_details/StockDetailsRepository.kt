package com.nbstocks.nbstocks.domain.repositories.stock_details

import com.nbstocks.nbstocks.common.Resource
import com.nbstocks.nbstocks.data.remote.model.DailyStocksDto
import kotlinx.coroutines.flow.Flow

interface StockDetailsRepository {
    suspend fun getStocksDetails(symbol:String):Flow<Resource<DailyStocksDto>>
}