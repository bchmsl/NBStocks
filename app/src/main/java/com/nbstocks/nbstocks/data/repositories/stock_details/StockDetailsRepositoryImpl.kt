package com.nbstocks.nbstocks.data.repositories.stock_details

import android.util.Log
import android.util.Log.d
import com.nbstocks.nbstocks.common.Resource
import com.nbstocks.nbstocks.common.mapper.toDailyStockDomainModelList
import com.nbstocks.nbstocks.csv.CSVParser
import com.nbstocks.nbstocks.csv.DailyListingsParser
import com.nbstocks.nbstocks.data.remote.model.DailyStockDto
import com.nbstocks.nbstocks.data.remote.services.StockDaily
import com.nbstocks.nbstocks.domain.model.DailyStockDomainModel
import com.nbstocks.nbstocks.domain.repositories.stock_details.StockDetailsRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import okhttp3.ResponseBody
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class StockDetailsRepositoryImpl @Inject constructor(private val api: StockDaily, private val dailyListingsParser: CSVParser<DailyStockDto>) :
    StockDetailsRepository {

    override suspend fun getStocksDetails(symbol: String): Flow<Resource<List<DailyStockDomainModel>>> = flow {
        emit(Resource.Loading(true))
        try {
            val response = api.getStocksDetails(symbol = symbol)
            val stocksList = dailyListingsParser.parse(response.byteStream())
            emit(Resource.Success(stocksList.toDailyStockDomainModelList()))
            emit(Resource.Loading(false))
        }catch (e: Throwable){
            emit(Resource.Error(e))
            Log.wtf("LOG: StockDetailsRepositoryImpl", "getStocksDetails: ${e.message}")
            emit(Resource.Loading(false))
        }
    }
}