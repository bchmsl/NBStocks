package com.nbstocks.nbstocks.data.repositories.watchlist_stock

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.ChildEventListener
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.nbstocks.nbstocks.common.handlers.Resource
import com.nbstocks.nbstocks.domain.model.CurrentStockDomainModel
import com.nbstocks.nbstocks.domain.repositories.watchlist_stock.WatchlistStockRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject

class WatchlistStockRepositoryImpl @Inject constructor(
    private val db: FirebaseDatabase,
    private val auth: FirebaseAuth
) : WatchlistStockRepository {

    private val stockList = mutableListOf<CurrentStockDomainModel>()

    private val _stockState = MutableStateFlow<Resource<List<CurrentStockDomainModel>>>(Resource.Success(emptyList()))
    var stockState = _stockState.asStateFlow()

    override suspend fun addWatchlistStock(currentStockDomainModel: CurrentStockDomainModel) {
        db.reference.child("Users").child(auth.currentUser!!.uid).child("Watchlist")
            .child(currentStockDomainModel.symbol).setValue(currentStockDomainModel)
    }

    override suspend fun removeWatchlistStock(currentStockDomainModel: CurrentStockDomainModel) {
        db.reference.child("Users").child(auth.currentUser!!.uid).child("Watchlist")
            .child(currentStockDomainModel.symbol).removeValue()
    }

    override suspend fun getWatchlistStock() {

        db.reference.child("Users").child(auth.currentUser!!.uid).child("Watchlist")
            .addChildEventListener(object : ChildEventListener {
                override fun onChildAdded(snapshot: DataSnapshot, previousChildName: String?) {
                    if (snapshot.exists()) {

                        snapshot.getValue(CurrentStockDomainModel::class.java)
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

}