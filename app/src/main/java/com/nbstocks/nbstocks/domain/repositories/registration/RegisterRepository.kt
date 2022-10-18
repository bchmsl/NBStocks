package com.nbstocks.nbstocks.domain.repositories.registration

import com.google.firebase.auth.AuthResult
import com.nbstocks.nbstocks.common.Resource

interface RegisterRepository {
    suspend fun register(email: String, password: String) : Resource<AuthResult>
}