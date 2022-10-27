package com.nbstocks.nbstocks.data.repositories.daily_stock

import com.nbstocks.nbstocks.common.handlers.Resource
import com.nbstocks.nbstocks.csv.CSVParser
import com.nbstocks.nbstocks.data.mapper.toStockPricesDomainModelList
import com.nbstocks.nbstocks.data.remote.model.StockPricesDto
import com.nbstocks.nbstocks.data.remote.services.StockPricesService
import com.nbstocks.nbstocks.domain.model.StockPricesDomainModel
import com.nbstocks.nbstocks.domain.repositories.daily_stock.DailyStockPricesRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class DailyStockPricesPricesRepositoryImpl @Inject constructor(
    private val api: StockPricesService,
    private val dailyListingsParser: CSVParser<StockPricesDto>
) : DailyStockPricesRepository {

    override suspend fun getStocksDetails(symbol: String, function:String): Flow<Resource<List<StockPricesDomainModel>>> =
        flow {
            emit(Resource.Loading(true))
            try {
                val response = api.getStocksDetails(symbol = symbol, function = function)
                val stocksList = dailyListingsParser.parse(response.byteStream())
                emit(Resource.Success(stocksList.toStockPricesDomainModelList()))
                emit(Resource.Loading(false))
            } catch (e: Throwable) {
                emit(Resource.Error(e))
                emit(Resource.Loading(false))
            }
        }
}