package com.nbstocks.nbstocks.presentation.ui.password_recovery

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.nbstocks.nbstocks.common.handlers.Resource
import com.nbstocks.nbstocks.data.repositories.password_recovery.ResetPasswordRepositoryImpl
import com.nbstocks.nbstocks.domain.repositories.password_recovery.ResetPasswordRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class PasswordRecoveryViewModel @Inject constructor(
    private val resetPasswordRepository: ResetPasswordRepositoryImpl
) : ViewModel() {

    private val _resetPasswordResponse = MutableSharedFlow<Resource<String>>()
    val resetPasswordResponse : SharedFlow<Resource<String>> = _resetPasswordResponse

    fun resetPassword(email: String) =
        viewModelScope.launch {
            _resetPasswordResponse.emit(Resource.Loading(true))
            withContext(Dispatchers.IO) {
                _resetPasswordResponse.emit(resetPasswordRepository.resetPassword(email))
            }
        }
}