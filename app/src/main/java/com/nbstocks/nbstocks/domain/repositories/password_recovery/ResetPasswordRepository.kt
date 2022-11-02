package com.nbstocks.nbstocks.domain.repositories.password_recovery

import com.nbstocks.nbstocks.common.handlers.Resource

interface ResetPasswordRepository {
    suspend fun resetPassword(email: String) : Resource<String>
}