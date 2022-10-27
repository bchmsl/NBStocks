package com.nbstocks.nbstocks.data.repositories.registration

import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseAuth
import com.nbstocks.nbstocks.common.handlers.Resource
import com.nbstocks.nbstocks.common.handlers.ResponseHandler
import com.nbstocks.nbstocks.data.remote.model.User
import com.nbstocks.nbstocks.data.repositories.db_add_user.DbAddUserRepositoryImpl
import com.nbstocks.nbstocks.domain.repositories.registration.RegisterRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.tasks.await
import kotlinx.coroutines.withContext
import javax.inject.Inject

class RegisterRepositoryImpl @Inject constructor(
    private val auth: FirebaseAuth,
    private val repository: DbAddUserRepositoryImpl
) : RegisterRepository,ResponseHandler {

    override suspend fun register(email: String, password: String)
            : Resource<AuthResult> =
        withContext(Dispatchers.IO) {
            return@withContext try {
                val result = auth.createUserWithEmailAndPassword(email, password).await()
                val currentUser = auth.currentUser?.uid
                val user = User(email, password)
                repository.addUserToDb(currentUser!!, user)
                handleSuccess(result)
            } catch (exception: Exception) {
                handleException(exception)
            }
        }

}