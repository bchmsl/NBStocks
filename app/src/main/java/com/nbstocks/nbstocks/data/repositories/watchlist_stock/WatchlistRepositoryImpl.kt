package com.nbstocks.nbstocks.data.repositories.watchlist_stock

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase
import com.nbstocks.nbstocks.common.extensions.addOnDataChangedListener
import com.nbstocks.nbstocks.common.handlers.Resource
import com.nbstocks.nbstocks.data.mapper.toWatchlistStockInfoDomainModel
import com.nbstocks.nbstocks.data.remote.services.WatchlistStockInfoService
import com.nbstocks.nbstocks.domain.model.WatchlistStockInfoDomainModel
import com.nbstocks.nbstocks.domain.repositories.base_repository.BaseRepository
import com.nbstocks.nbstocks.domain.repositories.watchlist_stock.WatchlistRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class WatchlistRepositoryImpl @Inject constructor(
    db: FirebaseDatabase,
    auth: FirebaseAuth,
) : WatchlistRepository {

    private val dbReference = db.reference
        .child("Users")
        .child(auth.currentUser!!.uid)
        .child("Watchlist")



    private val _watchlistItems =
        MutableStateFlow<Resource<List<String>>>(Resource.Success(emptyList()))
    var watchlistItems = _watchlistItems.asStateFlow()

    override suspend fun addWatchlistStock(symbol: String) {
        dbReference.child(symbol).setValue(symbol)
    }

    override suspend fun removeWatchlistStock(symbol: String) {
        dbReference.child(symbol).removeValue()
    }

    override suspend fun getWatchlistItems() {
        dbReference.addOnDataChangedListener { snapshot ->
            _watchlistItems.tryEmit(Resource.Loading(true))
            _watchlistItems.tryEmit(Resource.Success(snapshot.children.map { it.key.toString() }))
            _watchlistItems.tryEmit(Resource.Loading(false))
        }
    }

}