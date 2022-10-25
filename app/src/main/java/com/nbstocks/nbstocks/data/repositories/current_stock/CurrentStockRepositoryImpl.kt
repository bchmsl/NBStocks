package com.nbstocks.nbstocks.data.repositories.current_stock

import com.nbstocks.nbstocks.common.handlers.Resource
import com.nbstocks.nbstocks.data.mapper.toCurrentStockDomainModel
import com.nbstocks.nbstocks.data.remote.model.CurrentStockDto
import com.nbstocks.nbstocks.data.remote.services.CurrentStock
import com.nbstocks.nbstocks.domain.model.CurrentStockDomainModel
import com.nbstocks.nbstocks.domain.repositories.current_stock.CurrentStockRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class CurrentStockRepositoryImpl @Inject constructor(
    private val api: CurrentStock
):CurrentStockRepository {
    override suspend fun getCurrentStock(symbol: String): Flow<Resource<CurrentStockDomainModel>> =
        flow {
            emit(Resource.Loading(true))
            try {
                val response = api.getCurrentStock(symbol = symbol)
                emit(Resource.Success(response.body()!!.toCurrentStockDomainModel()))
                emit(Resource.Loading(false))
            }catch (e:Throwable){
                emit(Resource.Error(e))
                emit(Resource.Loading(false))
            }
    }
}