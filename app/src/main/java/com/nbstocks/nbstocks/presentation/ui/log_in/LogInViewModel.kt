package com.nbstocks.nbstocks.presentation.ui.log_in

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.auth.AuthResult
import com.nbstocks.nbstocks.common.handlers.Resource
import com.nbstocks.nbstocks.domain.repositories.login.LoginRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LogInViewModel @Inject constructor(private val repository: LoginRepository) : ViewModel() {

    private val _loginResponse = MutableSharedFlow<Resource<AuthResult>>()
    val loginResponse = _loginResponse.asSharedFlow()

    private val _loaderState = MutableStateFlow(false)
    val loaderState: StateFlow<Boolean> get() = _loaderState

    fun signIn(email: String, password: String) {
        viewModelScope.launch {
            _loaderState.emit(true)
            _loginResponse.emit(repository.login(email, password))
            _loaderState.emit(false)
        }
    }

}