package com.nbstocks.nbstocks.data.repositories.watchlist_stock

import android.util.Log
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.nbstocks.nbstocks.common.extensions.addOnDataChangedListener
import com.nbstocks.nbstocks.common.handlers.Resource
import com.nbstocks.nbstocks.data.mapper.toWatchlistStockInfoDomainModel
import com.nbstocks.nbstocks.data.remote.services.WatchlistStockInfoService
import com.nbstocks.nbstocks.domain.model.WatchlistStockInfoDomainModel
import com.nbstocks.nbstocks.domain.repositories.base_repository.BaseRepository
import com.nbstocks.nbstocks.domain.repositories.watchlist_stock.WatchlistStockRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class WatchlistStockRepositoryImpl @Inject constructor(
    db: FirebaseDatabase,
    auth: FirebaseAuth,
    private val api: WatchlistStockInfoService,
    private val baseRepository: BaseRepository
) : WatchlistStockRepository {

    private val dbReference = db.reference
        .child("Users")
        .child(auth.currentUser!!.uid)
        .child("Watchlist")


    private val stockList = mutableListOf<String>()

    private val _stockState =
        MutableStateFlow<Resource<List<String>>>(Resource.Success(emptyList()))
    var stockState = _stockState.asStateFlow()

    override suspend fun addWatchlistStock(symbol: String) {
        dbReference.child(symbol).setValue(symbol)
    }

    override suspend fun removeWatchlistStock(symbol: String) {
        dbReference.child(symbol).removeValue()
    }

    override suspend fun getHomeScreenItems() {
        stockList.clear()
        dbReference.addOnDataChangedListener { snapshot ->
            _stockState.tryEmit(Resource.Loading(true))
            _stockState.tryEmit(Resource.Success(snapshot.children.map { it.key.toString() }))
            _stockState.tryEmit(Resource.Loading(false))
        }
    }

    override suspend fun getWatchlistStocksInformation(symbols: String)
            : Flow<Resource<WatchlistStockInfoDomainModel>> = flow {
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