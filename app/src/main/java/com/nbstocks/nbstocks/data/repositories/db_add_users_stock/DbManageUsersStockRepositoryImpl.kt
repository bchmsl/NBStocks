package com.nbstocks.nbstocks.data.repositories.db_add_users_stock

import android.util.Log.d
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.nbstocks.nbstocks.common.extensions.doOnSuccess
import com.nbstocks.nbstocks.common.extensions.onChildAddedListener
import com.nbstocks.nbstocks.common.handlers.Resource
import com.nbstocks.nbstocks.data.repositories.get_stock_amount.GetStockAmountRepositoryImpl
import com.nbstocks.nbstocks.domain.model.UsersStockDomainModel
import com.nbstocks.nbstocks.domain.repositories.db_add_users_stock.DbManageUsersStockRepository
import com.nbstocks.nbstocks.domain.repositories.get_stock_amount.GetStockAmountRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collect
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

    override suspend fun sellUsersStock(usersStockDomainModel: UsersStockDomainModel) {
        dbReference.child(usersStockDomainModel.symbol).removeValue()
    }

    override suspend fun getUsersStock() {
        stockList.clear()
        dbReference.onChildAddedListener({ snapshot, _ ->
            if (snapshot.exists()) {
                snapshot.getValue(UsersStockDomainModel::class.java)
                    ?.let { stockList.add(it) }
                _stockState.tryEmit(Resource.Success(stockList.toList()))
            }
        },{snapshot, _ ->
            if (snapshot.exists()) {
                snapshot.getValue(UsersStockDomainModel::class.java)
                    ?.let {
                        stockList.find { element ->
                            element.symbol == it.symbol
                        }?.copy(
                            amountInStocks = it.amountInStocks,
                            price = it.price,
                            symbol = it.symbol
                        )
                    }
                _stockState.tryEmit(Resource.Success(stockList.toList()))
            }
            })
    }
}
