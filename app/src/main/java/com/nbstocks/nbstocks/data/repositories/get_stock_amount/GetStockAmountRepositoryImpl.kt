package com.nbstocks.nbstocks.data.repositories.get_stock_amount

import android.util.Log.d
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
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
        dbReference.child(symbol).addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                if (snapshot.exists()) {

                    val stockInfo = snapshot.getValue(UsersStockDomainModel::class.java)!!
                    d("Tag_rep","${stockInfo.amountInStocks}")
                    _stockAmount.tryEmit(Resource.Success(stockInfo.amountInStocks.toString()))

                    if (stockInfo.amountInStocks!! < 0){
                        _stockAmount.tryEmit(Resource.Error(error = Throwable("no stock")))
                    }
                }
            }

            override fun onCancelled(error: DatabaseError) {}

        })
    }


}