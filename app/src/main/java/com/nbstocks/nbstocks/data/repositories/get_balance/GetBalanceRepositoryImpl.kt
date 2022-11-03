package com.nbstocks.nbstocks.data.repositories.get_balance

import android.util.Log
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.nbstocks.nbstocks.common.handlers.Resource
import com.nbstocks.nbstocks.domain.model.UsersStockDomainModel
import com.nbstocks.nbstocks.domain.repositories.get_balance.GetBalanceRepository
import com.nbstocks.nbstocks.domain.repositories.get_stock_amount.GetStockAmountRepository
import com.nbstocks.nbstocks.presentation.ui.sign_up.model.User
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject

class GetBalanceRepositoryImpl @Inject constructor(
    db: FirebaseDatabase,
    auth: FirebaseAuth
) : GetBalanceRepository {

    private val dbReference = db.reference
        .child("Users")
        .child(auth.currentUser!!.uid)

    private val _balance = MutableStateFlow<Resource<String>>(Resource.Loading(true))
    var balance = _balance.asStateFlow()


    override suspend fun getBalance() {
        dbReference.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                if (snapshot.exists()) {

                    val userInfo = snapshot.getValue(User::class.java)!!
                    Log.d("Tag_rep", "${userInfo.balance}")
                    _balance.tryEmit(Resource.Success(userInfo.balance.toString()))

                    if (userInfo.balance!! < 0) {
                        _balance.tryEmit(Resource.Error(error = Throwable("no balance")))
                    }
                }
            }

            override fun onCancelled(error: DatabaseError) {}
        })
    }

    override suspend fun changeBalance(money: Double) {
        dbReference.child("balance").setValue(money)
    }


}