package com.nbstocks.nbstocks.domain.repositories.current_stock

import com.nbstocks.nbstocks.common.handlers.Resource
import com.nbstocks.nbstocks.data.remote.model.CurrentStockDto
import com.nbstocks.nbstocks.domain.model.CurrentStockDomainModel
import com.nbstocks.nbstocks.domain.model.DailyStockDomainModel
import kotlinx.coroutines.flow.Flow

interface CurrentStockRepository {
    suspend fun getCurrentStock(symbol:String): Flow<Resource<CurrentStockDomainModel>>
}