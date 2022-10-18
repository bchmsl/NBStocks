package com.nbstocks.nbstocks.domain.repositories.login

import com.google.firebase.auth.AuthResult
import com.nbstocks.nbstocks.common.Resource

interface LoginRepository {
    suspend fun login(email: String, password: String): Resource<AuthResult>
}