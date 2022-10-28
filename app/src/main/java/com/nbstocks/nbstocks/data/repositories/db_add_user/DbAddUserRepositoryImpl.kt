package com.nbstocks.nbstocks.data.repositories.db_add_user

import com.google.firebase.database.FirebaseDatabase
import com.nbstocks.nbstocks.common.handlers.Resource
import com.nbstocks.nbstocks.common.handlers.ResponseHandler
import com.nbstocks.nbstocks.data.remote.model.User
import com.nbstocks.nbstocks.domain.repositories.db_add_user.DbAddUserRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.tasks.await
import kotlinx.coroutines.withContext
import javax.inject.Inject

class DbAddUserRepositoryImpl @Inject constructor(
    private val db: FirebaseDatabase
) : DbAddUserRepository, ResponseHandler {

    override suspend fun addUserToDb(uid: String, user: User): Resource<Void> =
        withContext(Dispatchers.IO) {
            return@withContext try {
                val dbReference = db.getReference("Users")
                val result = dbReference.child(uid).setValue(user).await()
                handleSuccess(result)
            } catch (e: Exception) {
                handleException(e)
            }
        }

}