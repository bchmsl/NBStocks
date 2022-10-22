package com.nbstocks.nbstocks.data.repositories.stock_details

import android.util.Log.d
import com.nbstocks.nbstocks.common.Resource
import com.nbstocks.nbstocks.data.remote.model.DailyStocksDto
import com.nbstocks.nbstocks.data.remote.services.StockDaily
import com.nbstocks.nbstocks.domain.repositories.stock_details.StockDetailsRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class StockDetailsRepositoryImpl @Inject constructor(private val api: StockDaily) :
    StockDetailsRepository {
    override suspend fun getStocksDetails(symbol: String): Flow<Resource<DailyStocksDto>> = flow {
        val response = api.getStocksDetails(symbol)
        d("logii", "nika")
    }
}