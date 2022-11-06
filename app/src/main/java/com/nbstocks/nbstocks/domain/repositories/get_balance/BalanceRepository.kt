package com.nbstocks.nbstocks.domain.repositories.get_balance

interface BalanceRepository {
    suspend fun getBalance()
    suspend fun changeBalance(money: Double)
}