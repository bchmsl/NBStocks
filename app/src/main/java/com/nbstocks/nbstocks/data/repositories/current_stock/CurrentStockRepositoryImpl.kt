package com.nbstocks.nbstocks.data.repositories.current_stock

import com.nbstocks.nbstocks.common.handlers.Resource
import com.nbstocks.nbstocks.data.mapper.toCurrentStockDomainModel
import com.nbstocks.nbstocks.data.remote.services.CurrentStockPriceService
import com.nbstocks.nbstocks.domain.model.CurrentStockDomainModel
import com.nbstocks.nbstocks.domain.repositories.current_stock.CurrentStockRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class CurrentStockRepositoryImpl @Inject constructor(
    private val api: CurrentStockPriceService,
) : CurrentStockRepository {
    override suspend fun getCurrentStock(symbol: String): Flow<Resource<CurrentStockDomainModel>> =
        flow {
            emit(Resource.Loading(true))
            try {
                val response = api.getCurrentStockPrice(symbol = symbol)
                if (response.isSuccessful) {
                    val body = response.body()
                    if (body != null) {
                        emit(Resource.Success(body.toCurrentStockDomainModel(symbol)))
                    }
                }
                emit(Resource.Loading(false))
            } catch (e: Throwable) {
                emit(Resource.Error(e))
                emit(Resource.Loading(false))
            }
        }
}