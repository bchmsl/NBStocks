package com.nbstocks.nbstocks.data.repositories.db_balance

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase
import com.nbstocks.nbstocks.common.extensions.addOnDataChangedListener
import com.nbstocks.nbstocks.common.handlers.Resource
import com.nbstocks.nbstocks.domain.repositories.get_balance.BalanceRepository
import com.nbstocks.nbstocks.presentation.ui.sign_up.model.User
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject

class BalanceRepositoryImpl @Inject constructor(
    db: FirebaseDatabase,
    auth: FirebaseAuth
) : BalanceRepository {

    private val dbReference = db.reference
        .child("Users")
        .child(auth.currentUser!!.uid)

    private val _balance = MutableStateFlow<Resource<String>>(Resource.Loading(true))
    var balance = _balance.asStateFlow()


    override suspend fun getBalance() {
        dbReference.addOnDataChangedListener { snapshot ->
            _balance.tryEmit(Resource.Loading(true))
            if (snapshot.exists()) {
                val userInfo = snapshot.getValue(User::class.java)!!
                _balance.tryEmit(Resource.Success(userInfo.balance.toString()))
                if (userInfo.balance!! < 0) {
                    _balance.tryEmit(Resource.Error(error = Throwable("no balance")))
                }
            }
            _balance.tryEmit(Resource.Loading(false))
        }
    }

    override suspend fun changeBalance(money: Double) {
        dbReference.child("balance").setValue(money)
    }


}