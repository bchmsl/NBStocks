package com.nbstocks.nbstocks.presentation.ui.profile

import android.app.Application
import android.content.Context
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.auth.FirebaseAuth
import com.nbstocks.nbstocks.App
import com.nbstocks.nbstocks.data.local.datastore.DatastoreProvider.readPreference
import com.nbstocks.nbstocks.data.local.datastore.DatastoreProvider.savePreference
import com.nbstocks.nbstocks.data.repositories.change_password.ChangePasswordRepositoryImpl
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProfileViewModel @Inject constructor(
    private val auth: FirebaseAuth,
    private val changePasswordRepositoryImpl: ChangePasswordRepositoryImpl,
) : ViewModel() {

//    private val _changePasswordResponse = MutableSharedFlow<Resource<String>>()
//    val changePasswordResponse = _changePasswordResponse
//
//    fun changePassword(password: String) {
//        viewModelScope.launch {
//            _changePasswordResponse.emit(Resource.Loading(true))
//            withContext(Dispatchers.IO) {
//                try {
//                    _changePasswordResponse.emit(
//                        changePasswordRepositoryImpl.changePassword(
//                            password
//                        )
//                    )
//                } catch (e: java.lang.Exception) {
//                    _changePasswordResponse.emit(Resource.Error(e))
//                }
//            }
//        }
//    }

//    fun signOut() {
//        viewModelScope.launch {
//            auth.signOut()
//        }
//    }

    fun setShownBalance(
        context: Context,
        isShown: Boolean,
    ) {
        viewModelScope.launch {
            context.savePreference(isShown)
            context.readPreference(true).let {
                Log.wtf("TAG_BALANCE", it.toString())
            }

        }
    }

    fun getShownBalanceState(
        context: Context
    ) = flow {
        emit(context.readPreference(true))

    }
}