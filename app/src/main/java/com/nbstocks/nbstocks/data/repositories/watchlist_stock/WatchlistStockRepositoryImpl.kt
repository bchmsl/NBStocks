package com.nbstocks.nbstocks.data.repositories.watchlist_stock

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.ChildEventListener
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.nbstocks.nbstocks.common.handlers.Resource
import com.nbstocks.nbstocks.data.mapper.toWatchlistStockInfoDomainModel
import com.nbstocks.nbstocks.data.remote.services.WatchlistStockInfoService
import com.nbstocks.nbstocks.domain.model.WatchlistStockInfoDomainModel
import com.nbstocks.nbstocks.domain.repositories.watchlist_stock.WatchlistStockRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class WatchlistStockRepositoryImpl @Inject constructor(
    private val db: FirebaseDatabase,
    private val auth: FirebaseAuth,
    private val api: WatchlistStockInfoService
) : WatchlistStockRepository {

    private val stockList = mutableListOf<String>()

    private val _stockState =
        MutableStateFlow<Resource<List<String>>>(Resource.Success(emptyList()))
    var stockState = _stockState.asStateFlow()

    override suspend fun addWatchlistStock(symbol: String) {
        db.reference.child("Users").child(auth.currentUser!!.uid).child("Watchlist")
            .child(symbol).setValue(symbol)
    }

    override suspend fun removeWatchlistStock(symbol: String) {
        db.reference.child("Users").child(auth.currentUser!!.uid).child("Watchlist")
            .child(symbol).removeValue()
    }

    override suspend fun getWatchlistItems() {
        db.reference.child("Users").child(auth.currentUser!!.uid).child("Watchlist")
            .addChildEventListener(object : ChildEventListener {
                override fun onChildAdded(snapshot: DataSnapshot, previousChildName: String?) {
                    if (snapshot.exists()) {

                        snapshot.getValue(String::class.java)
                            ?.let { stockList.add(it) }
                        _stockState.tryEmit(Resource.Success(stockList))

                    }
                }

                override fun onChildChanged(snapshot: DataSnapshot, previousChildName: String?) {}
                override fun onChildRemoved(snapshot: DataSnapshot) {}
                override fun onChildMoved(snapshot: DataSnapshot, previousChildName: String?) {}
                override fun onCancelled(error: DatabaseError) {}
            })
    }

    override suspend fun getWatchlistStocksInformation(symbols: String)
            : Flow<Resource<WatchlistStockInfoDomainModel>> = flow{
        emit(Resource.Loading(true))
        try {
            val response =
                api.getWatchlistStockInfo(symbols = symbols)
            if (response.isSuccessful) {
                val body = response.body()
                if (body != null) {
                    emit(Resource.Success(body.toWatchlistStockInfoDomainModel()))
                } else {
                    emit(Resource.Error(Throwable("No data found")))
                }
            } else {
                emit(Resource.Error(Throwable(response.message())))
            }
            emit(Resource.Loading(false))
        } catch (e: Throwable) {
            emit(Resource.Error(e))
            emit(Resource.Loading(false))
        }
    }
}