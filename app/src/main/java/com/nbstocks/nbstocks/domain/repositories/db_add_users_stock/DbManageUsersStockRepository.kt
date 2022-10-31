package com.nbstocks.nbstocks.domain.repositories.db_add_users_stock

import com.nbstocks.nbstocks.domain.model.UsersStockDomainModel

interface DbManageUsersStockRepository {
    suspend fun buyUsersStock(usersStockDomainModel: UsersStockDomainModel)
    suspend fun getUsersStock()
    suspend fun sellUsersStock(usersStockDomainModel: UsersStockDomainModel)
}