package com.nbstocks.nbstocks.data.repositories.trade_history

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase
import com.nbstocks.nbstocks.common.extensions.addOnDataChangedListener
import com.nbstocks.nbstocks.common.handlers.Resource
import com.nbstocks.nbstocks.domain.model.TradeHistoryDomainModel
import com.nbstocks.nbstocks.domain.repositories.trade_history.TradeHistoryRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject

class TradeHistoryRepositoryImpl @Inject constructor(
    auth: FirebaseAuth,
    db: FirebaseDatabase
): TradeHistoryRepository {

    private val dbReference = db.reference
        .child("Users")
        .child(auth.currentUser!!.uid)
        .child("TradeHistory")

    private val _tradeHistoryState = MutableStateFlow<Resource<List<TradeHistoryDomainModel>>>(Resource.Success(emptyList()))
    var tradeHistoryState = _tradeHistoryState.asStateFlow()


    override suspend fun getTradeHistory() {
        dbReference.addOnDataChangedListener { snapshot ->
            _tradeHistoryState.tryEmit(Resource.Loading(true))
            _tradeHistoryState.tryEmit(Resource.Success(snapshot.children.map {

                TradeHistoryDomainModel(
                    isBuy = it.child("buy").value.toString().toBoolean(),
                    symbol = it.child("symbol").value.toString(),
                    money = it.child("money").value.toString(),
                    tradeDate = it.child("tradeDate").value.toString()
                )

            }))
            _tradeHistoryState.tryEmit(Resource.Loading(false))
        }
    }

    override suspend fun addTradeHistoryItem(tradeHistoryDomainModel: TradeHistoryDomainModel) {
        dbReference.child(tradeHistoryDomainModel.tradeDate).setValue(tradeHistoryDomainModel)
    }
}