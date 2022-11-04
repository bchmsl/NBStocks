package com.nbstocks.nbstocks.domain.repositories.change_password

import com.nbstocks.nbstocks.common.handlers.Resource


interface ChangePasswordRepository {
    suspend fun changePassword(password: String): Resource<String>
}