package com.nbstocks.nbstocks.presentation.ui.sign_up

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.auth.AuthResult
import com.nbstocks.nbstocks.common.handlers.Resource
import com.nbstocks.nbstocks.data.repositories.registration.RegisterRepositoryImpl
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject


@HiltViewModel
class SignUpViewModel @Inject constructor(private val repository: RegisterRepositoryImpl) : ViewModel() {

    private val _registerResponse = MutableSharedFlow<Resource<AuthResult>>()
    val registerResponse : SharedFlow<Resource<AuthResult>> = _registerResponse

    fun signUp(email: String, password: String) =
        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                _registerResponse.emit(
                    repository.register(email, password)
                )
            }
        }

}