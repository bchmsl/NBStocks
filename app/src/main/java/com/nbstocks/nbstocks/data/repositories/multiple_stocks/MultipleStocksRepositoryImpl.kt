package com.nbstocks.nbstocks.data.repositories.multiple_stocks

import com.nbstocks.nbstocks.common.handlers.Resource
import com.nbstocks.nbstocks.data.mapper.toWatchlistStockInfoDomainModel
import com.nbstocks.nbstocks.data.remote.services.WatchlistStockInfoService
import com.nbstocks.nbstocks.domain.model.WatchlistStockInfoDomainModel
import com.nbstocks.nbstocks.domain.repositories.base_repository.BaseRepository
import com.nbstocks.nbstocks.domain.repositories.multiple_stocks.MultipleStocksRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class MultipleStocksRepositoryImpl @Inject constructor(
    private val api: WatchlistStockInfoService,
    private val baseRepository: BaseRepository
): MultipleStocksRepository {

    override suspend fun getWatchlistStocksInformation(symbols: String): Flow<Resource<WatchlistStockInfoDomainModel>> = flow {
        emit(Resource.Loading(true))
        if (symbols.isNotBlank()) {
            val resource = baseRepository.safeApiCall({
                api.getWatchlistStockInfo(symbols = symbols)
            }, { toWatchlistStockInfoDomainModel() })
            emit(resource)
        }
        emit(Resource.Loading(false))
    }

}