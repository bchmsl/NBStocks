package com.nbstocks.nbstocks.domain.model

data class TradeHistoryDomainModel(
    val isBuy: Boolean = false,
    val symbol:String = "",
    val money:String = "",
    val tradeDate:String = ""
)