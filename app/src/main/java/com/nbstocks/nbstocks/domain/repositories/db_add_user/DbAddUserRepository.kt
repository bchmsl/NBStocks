package com.nbstocks.nbstocks.domain.repositories.db_add_user

import com.nbstocks.nbstocks.common.Resource
import com.nbstocks.nbstocks.data.remote.model.User

interface DbAddUserRepository {
    suspend fun addUserToDb(uid: String, user: User): Resource<Void>
}