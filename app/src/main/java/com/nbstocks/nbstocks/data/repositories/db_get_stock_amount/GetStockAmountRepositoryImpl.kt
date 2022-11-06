package com.nbstocks.nbstocks.data.repositories.db_get_stock_amount

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase
import com.nbstocks.nbstocks.common.extensions.addOnDataChangedListener
import com.nbstocks.nbstocks.common.handlers.Resource
import com.nbstocks.nbstocks.domain.model.UsersStockDomainModel
import com.nbstocks.nbstocks.domain.repositories.get_stock_amount.GetStockAmountRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject

class GetStockAmountRepositoryImpl @Inject constructor(
    db: FirebaseDatabase,
    auth: FirebaseAuth
) : GetStockAmountRepository {

    private val dbReference = db.reference
        .child("Users")
        .child(auth.currentUser!!.uid)
        .child("OwnedStocks")

    private val _stockAmount = MutableStateFlow<Resource<String>>(Resource.Loading(true))
    var stockAmount = _stockAmount.asStateFlow()


    override suspend fun getStockAmount(symbol: String) {
        dbReference.child(symbol).addOnDataChangedListener { snapshot ->
            if (snapshot.exists()) {

                val stockInfo = snapshot.getValue(UsersStockDomainModel::class.java)!!
                _stockAmount.tryEmit(Resource.Success(stockInfo.amountInStocks.toString()))

                if (stockInfo.amountInStocks!! < 0) {
                    _stockAmount.tryEmit(Resource.Error(error = Throwable("no stock")))
                }
            }
        }
    }


}