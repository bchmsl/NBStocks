package com.nbstocks.nbstocks.data.repositories.trade_history

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase
import com.nbstocks.nbstocks.domain.model.TradeHistoryDomainModel
import com.nbstocks.nbstocks.domain.repositories.trade_history.TradeHistoryRepository
import javax.inject.Inject

class TradeHistoryRepositoryImpl @Inject constructor(
    auth: FirebaseAuth,
    db: FirebaseDatabase
): TradeHistoryRepository {

    private val dbReference = db.reference
        .child("Users")
        .child(auth.currentUser!!.uid)
        .child("TradeHistory")


    override suspend fun getTradeHistory() {

    }

    override suspend fun addTradeHistoryItem(tradeHistoryDomainModel: TradeHistoryDomainModel) {
        dbReference.child(tradeHistoryDomainModel.tradeDate).setValue(tradeHistoryDomainModel)
    }
}