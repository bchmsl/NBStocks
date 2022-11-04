package com.nbstocks.nbstocks.presentation.ui.change_password

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.auth.FirebaseAuth
import com.nbstocks.nbstocks.common.handlers.Resource
import com.nbstocks.nbstocks.common.handlers.ResponseHandler
import com.nbstocks.nbstocks.domain.repositories.change_password.ChangePasswordRepository
import com.opencsv.bean.util.OpencsvUtils.handleException
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class PasswordChangeViewModel @Inject constructor(
    private val changePasswordRepository: ChangePasswordRepository,
) : ViewModel() {

    private val _changePasswordResponse = MutableSharedFlow<Resource<String>>()
    val changePasswordResponse : SharedFlow<Resource<String>> = _changePasswordResponse

    fun changePassword(password: String, confirmPassword:String) {

        viewModelScope.launch {
            if (password != confirmPassword){
                _changePasswordResponse.emit(Resource.Error(Throwable("Password doesn't match")))
            }else{
                _changePasswordResponse.emit(Resource.Loading(true))
                withContext(Dispatchers.IO) {
                    try {
                        _changePasswordResponse.emit(changePasswordRepository.changePassword(password))
                    } catch (e: java.lang.Exception) {
                        _changePasswordResponse.emit(Resource.Error(Throwable("Error")))
                    }
                }
            }
        }
    }
}
