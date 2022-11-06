package com.nbstocks.nbstocks.data.repositories.current_stock

import com.nbstocks.nbstocks.common.handlers.Resource
import com.nbstocks.nbstocks.data.mapper.toCurrentStockDomainModel
import com.nbstocks.nbstocks.data.remote.services.CurrentStockPriceService
import com.nbstocks.nbstocks.domain.model.CurrentStockDomainModel
import com.nbstocks.nbstocks.domain.repositories.base_repository.BaseRepository
import com.nbstocks.nbstocks.domain.repositories.current_stock.CurrentStockRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class CurrentStockRepositoryImpl @Inject constructor(
    private val api: CurrentStockPriceService,
    private val baseRepository: BaseRepository
) : CurrentStockRepository {
    override suspend fun getCurrentStock(symbol: String): Flow<Resource<CurrentStockDomainModel>> =
        flow {
            emit(Resource.Loading(true))
            val resource = baseRepository.safeApiCall({
                api.getCurrentStockPrice(symbol = symbol)
            }, {
                toCurrentStockDomainModel(symbol)
            })
            emit(resource)
            emit(Resource.Loading(false))
        }
}