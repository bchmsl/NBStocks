package com.nbstocks.nbstocks.data.repositories.db_owned_stocks

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase
import com.nbstocks.nbstocks.common.extensions.addOnDataChangedListener
import com.nbstocks.nbstocks.common.handlers.Resource
import com.nbstocks.nbstocks.domain.model.UsersStockDomainModel
import com.nbstocks.nbstocks.domain.repositories.db_add_users_stock.OwnedStocksRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject

class OwnedStocksRepositoryImpl @Inject constructor(
    db: FirebaseDatabase,
    auth: FirebaseAuth,
    ) : OwnedStocksRepository {

    private val dbReference = db.reference
        .child("Users")
        .child(auth.currentUser!!.uid)
        .child("OwnedStocks")

    private val _ownedStockState = MutableStateFlow<Resource<List<UsersStockDomainModel>>>(Resource.Success(emptyList()))
    var ownedStockState = _ownedStockState.asStateFlow()


    override suspend fun buyStocks(usersStockDomainModel: UsersStockDomainModel) {
        dbReference.child(usersStockDomainModel.symbol).setValue(usersStockDomainModel)
    }

    override suspend fun sellOwnedStocks(symbol:String) {
        dbReference.child(symbol).removeValue()
    }

    override suspend fun getOwnedStocks() {
        dbReference.addOnDataChangedListener { snapshot ->
            _ownedStockState.tryEmit(Resource.Loading(true))
            _ownedStockState.tryEmit(Resource.Success(snapshot.children.map {
                UsersStockDomainModel(
                    symbol = it.child("symbol").value.toString(),
                    price = it.child("price").value.toString(),
                    amountInStocks = it.child("amountInStocks").value.toString().toDoubleOrNull()
                )
            }))
            _ownedStockState.tryEmit(Resource.Loading(false))
        }

    }
}
