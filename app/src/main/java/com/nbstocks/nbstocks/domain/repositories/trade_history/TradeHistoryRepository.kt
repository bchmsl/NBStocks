package com.nbstocks.nbstocks.domain.repositories.trade_history

import com.nbstocks.nbstocks.domain.model.TradeHistoryDomainModel

interface TradeHistoryRepository {
    suspend fun getTradeHistory()
    suspend fun addTradeHistoryItem(tradeHistoryDomainModel: TradeHistoryDomainModel)
}