package com.nbstocks.nbstocks.domain.repositories.db_add_user

import com.nbstocks.nbstocks.common.handlers.Resource
import com.nbstocks.nbstocks.presentation.ui.sign_up.model.User

interface DbAddUserRepository {
    suspend fun addUserToDb(uid: String, user: User): Resource<Void>
}