package com.nbstocks.nbstocks.data.repositories.password_recovery

import com.google.firebase.auth.FirebaseAuth
import com.nbstocks.nbstocks.common.handlers.Resource
import com.nbstocks.nbstocks.common.handlers.ResponseHandler
import com.nbstocks.nbstocks.domain.repositories.password_recovery.ResetPasswordRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.tasks.await
import kotlinx.coroutines.withContext
import javax.inject.Inject

class ResetPasswordRepositoryImpl @Inject constructor(
    private val auth: FirebaseAuth,
) : ResetPasswordRepository,ResponseHandler {

    override suspend fun resetPassword(email: String): Resource<String> =
        withContext(Dispatchers.IO) {
            return@withContext try {
                auth.sendPasswordResetEmail(email).await()
                handleSuccess()
            } catch (e: Throwable) {
                handleException(e)
            }
        }

}