package com.nbstocks.nbstocks.data.repositories.db_manage_users_stock

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase
import com.nbstocks.nbstocks.common.extensions.addOnDataChangedListener
import com.nbstocks.nbstocks.common.handlers.Resource
import com.nbstocks.nbstocks.domain.model.UsersStockDomainModel
import com.nbstocks.nbstocks.domain.repositories.db_add_users_stock.DbManageUsersStockRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject

class DbManageUsersStockRepositoryImpl @Inject constructor(
    db: FirebaseDatabase,
    auth: FirebaseAuth,
    ) : DbManageUsersStockRepository {

    private val dbReference = db.reference
        .child("Users")
        .child(auth.currentUser!!.uid)
        .child("OwnedStocks")

    private val stockList = mutableListOf<UsersStockDomainModel>()


    private val _stockState =
        MutableStateFlow<Resource<List<UsersStockDomainModel>>>(Resource.Success(emptyList()))
    var stockState = _stockState.asStateFlow()


    override suspend fun buyUsersStock(usersStockDomainModel: UsersStockDomainModel) {
        dbReference.child(usersStockDomainModel.symbol).setValue(usersStockDomainModel)
    }

    override suspend fun sellUsersStock(symbol:String) {
        dbReference.child(symbol).removeValue()
    }

    override suspend fun getUsersStock() {
        stockList.clear()
        dbReference.addOnDataChangedListener { snapshot ->
            _stockState.tryEmit(Resource.Loading(true))
            _stockState.tryEmit(Resource.Success(snapshot.children.map {
                UsersStockDomainModel(
                    symbol = it.child("symbol").value.toString(),
                    price = it.child("price").value.toString(),
                    amountInStocks = it.child("amountInStocks").value.toString().toDoubleOrNull()
                )
            }))
            _stockState.tryEmit(Resource.Loading(false))
        }

    }
}
