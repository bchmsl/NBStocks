package com.nbstocks.nbstocks.data.repositories.daily_stock

import com.nbstocks.nbstocks.common.handlers.Resource
import com.nbstocks.nbstocks.csv.CSVParser
import com.nbstocks.nbstocks.data.mapper.toDailyStockDomainModelList
import com.nbstocks.nbstocks.data.remote.model.DailyStockDto
import com.nbstocks.nbstocks.data.remote.services.StockDaily
import com.nbstocks.nbstocks.domain.model.DailyStockDomainModel
import com.nbstocks.nbstocks.domain.repositories.daily_stock.DailyStockRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class DailyStockRepositoryImpl @Inject constructor(
    private val api: StockDaily,
    private val dailyListingsParser: CSVParser<DailyStockDto>
) : DailyStockRepository {

    override suspend fun getStocksDetails(symbol: String): Flow<Resource<List<DailyStockDomainModel>>> =
        flow {
            emit(Resource.Loading(true))
            try {
                val response = api.getStocksDetails(symbol = symbol)
                val stocksList = dailyListingsParser.parse(response.byteStream())
                emit(Resource.Success(stocksList.toDailyStockDomainModelList()))
                emit(Resource.Loading(false))
            } catch (e: Throwable) {
                emit(Resource.Error(e))
                emit(Resource.Loading(false))
            }
        }
}