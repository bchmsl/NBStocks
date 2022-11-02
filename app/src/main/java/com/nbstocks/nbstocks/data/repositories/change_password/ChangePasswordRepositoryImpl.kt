package com.nbstocks.nbstocks.data.repositories.change_password

import com.google.firebase.auth.FirebaseAuth
import com.nbstocks.nbstocks.common.handlers.Resource
import com.nbstocks.nbstocks.common.handlers.ResponseHandler
import com.nbstocks.nbstocks.domain.repositories.change_password.ChangePasswordRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class ChangePasswordRepositoryImpl @Inject constructor(
    private val auth: FirebaseAuth,
) : ChangePasswordRepository, ResponseHandler {

    override suspend fun changePassword(password: String): Resource<String> =
        withContext(Dispatchers.IO) {
            return@withContext try {
                auth.currentUser?.updatePassword(password)
                handleSuccess()
            } catch (e: Exception) {
                handleException<String>(e)
            }
        }
}