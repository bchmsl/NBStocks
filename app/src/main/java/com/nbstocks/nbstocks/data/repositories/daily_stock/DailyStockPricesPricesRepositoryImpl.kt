package com.nbstocks.nbstocks.data.repositories.daily_stock

import com.nbstocks.nbstocks.common.handlers.Resource
import com.nbstocks.nbstocks.data.mapper.toIntervalStockPricesDomainModel
import com.nbstocks.nbstocks.data.remote.services.IntervalStockPricesService
import com.nbstocks.nbstocks.domain.model.IntervalStockPricesDomainModel
import com.nbstocks.nbstocks.domain.repositories.base_repository.BaseRepository
import com.nbstocks.nbstocks.domain.repositories.daily_stock.DailyStockPricesRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class DailyStockPricesPricesRepositoryImpl @Inject constructor(
    private val api: IntervalStockPricesService,
    private val baseRepository: BaseRepository
) : DailyStockPricesRepository {

    override suspend fun getStocksDetails(
        symbol: String,
        range: String,
        interval: String
    ): Flow<Resource<IntervalStockPricesDomainModel?>> =
        flow {
            emit(Resource.Loading(true))
            val resource = baseRepository.safeApiCall({
                api.getIntervalStockPrices(symbol = symbol, range = range, interval = interval)
            },{
                toIntervalStockPricesDomainModel()
            })
            emit(resource)
            emit(Resource.Loading(false))
        }
}
