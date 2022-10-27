package com.nbstocks.nbstocks.data.repositories.current_stock

import android.util.Log
import com.nbstocks.nbstocks.common.constants.ModuleParams
import com.nbstocks.nbstocks.common.constants.StockPricesRequestFunctions
import com.nbstocks.nbstocks.common.handlers.Resource
import com.nbstocks.nbstocks.csv.CSVParser
import com.nbstocks.nbstocks.csv.StockPricesParser
import com.nbstocks.nbstocks.data.mapper.toCurrentStockDomainModel
import com.nbstocks.nbstocks.data.mapper.toCurrentStockDomainModelList
import com.nbstocks.nbstocks.data.mapper.toStockPricesDomainModelList
import com.nbstocks.nbstocks.data.remote.model.CurrentStockDto
import com.nbstocks.nbstocks.data.remote.model.StockPricesDto
import com.nbstocks.nbstocks.data.remote.services.StockPricesService
import com.nbstocks.nbstocks.domain.model.CurrentStockDomainModel
import com.nbstocks.nbstocks.domain.repositories.current_stock.CurrentStockRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject
import javax.inject.Named
import javax.inject.Singleton

@Singleton
class CurrentStockRepositoryImpl @Inject constructor(
    private val api: StockPricesService,
    @Named(ModuleParams.CURRENT_STOCK_PARSER)
    private val currentStockParser: CSVParser<CurrentStockDto>
):CurrentStockRepository {
    override suspend fun getCurrentStock(symbol: String): Flow<Resource<CurrentStockDomainModel>> =
        flow {
            emit(Resource.Loading(true))
            try {
                val response = api.getStocksDetails(symbol = symbol, function = StockPricesRequestFunctions.GLOBAL_QUOTE)
                val stocksList = currentStockParser.parse(response.byteStream())
                Log.w("TAG", stocksList.toString())
                emit(Resource.Success(stocksList.toCurrentStockDomainModelList()[0]))
                emit(Resource.Loading(false))
            } catch (e: Throwable) {
                emit(Resource.Error(e))
                emit(Resource.Loading(false))
            }
        }
}