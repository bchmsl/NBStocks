package com.nbstocks.nbstocks.domain.repositories.get_balance

interface GetBalanceRepository {
    suspend fun getBalance()
    suspend fun changeBalance(money: Double)
}