package com.nbstocks.nbstocks.data.repositories.db_add_users_stock

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.ChildEventListener
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.nbstocks.nbstocks.common.handlers.Resource
import com.nbstocks.nbstocks.domain.model.UsersStockDomainModel
import com.nbstocks.nbstocks.domain.repositories.db_add_users_stock.DbManageUsersStockRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject

class DbManageUsersStockRepositoryImpl @Inject constructor(
    private val db: FirebaseDatabase,
    private val auth: FirebaseAuth
) : DbManageUsersStockRepository {

    private val stockList = mutableListOf<UsersStockDomainModel>()

    private val _stockState =
        MutableStateFlow<Resource<List<UsersStockDomainModel>>>(Resource.Success(emptyList()))
    var stockState = _stockState.asStateFlow()

    override suspend fun buyUsersStock(usersStockDomainModel: UsersStockDomainModel) {

        if (stockList.contains(usersStockDomainModel)) {

            db.reference.child("Users").child(auth.currentUser!!.uid)
                .child("OwnedStocks")
                .child(usersStockDomainModel.symbol)
                .setValue(
                    UsersStockDomainModel(
                        symbol = usersStockDomainModel.symbol,
                        price = usersStockDomainModel.price,
                        amountInStocks = (usersStockDomainModel.amountInStocks.toDouble()).toString()
                    )
                )

        } else {
            db.reference.child("Users").child(auth.currentUser!!.uid).child("OwnedStocks")
                .child(usersStockDomainModel.symbol).setValue(usersStockDomainModel)
        }

    }

    override suspend fun sellUsersStock(usersStockDomainModel: UsersStockDomainModel) {
        db.reference.child("Users").child(auth.currentUser!!.uid).child("OwnedStocks")
            .child(usersStockDomainModel.symbol).removeValue()
    }

    override suspend fun getUsersStock() {
        db.reference.child("Users").child(auth.currentUser!!.uid).child("OwnedStocks")
            .addChildEventListener(object : ChildEventListener {
                override fun onChildAdded(snapshot: DataSnapshot, previousChildName: String?) {
                    if (snapshot.exists()) {

                        snapshot.getValue(UsersStockDomainModel::class.java)
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
