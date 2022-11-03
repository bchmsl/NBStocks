package com.nbstocks.nbstocks.presentation.ui.profile

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.auth.FirebaseAuth
import com.nbstocks.nbstocks.common.handlers.Resource
import com.nbstocks.nbstocks.data.repositories.change_password.ChangePasswordRepositoryImpl
import com.nbstocks.nbstocks.domain.repositories.change_password.ChangePasswordRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class ProfileViewModel @Inject constructor(
    private val auth: FirebaseAuth,
    private val changePasswordRepositoryImpl: ChangePasswordRepositoryImpl
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
}