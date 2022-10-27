package com.nbstocks.nbstocks.domain.repositories.current_stock

import com.nbstocks.nbstocks.common.handlers.Resource
import com.nbstocks.nbstocks.domain.model.CurrentStockDomainModel
import kotlinx.coroutines.flow.Flow

interface CurrentStockRepository {
    suspend fun getCurrentStock(symbol:String): Flow<Resource<CurrentStockDomainModel>>
}