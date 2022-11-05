package com.nbstocks.nbstocks.presentation.mapper

import com.nbstocks.nbstocks.domain.model.TradeHistoryDomainModel
import com.nbstocks.nbstocks.presentation.ui.home.model.TradeHistoryUiModel

fun TradeHistoryDomainModel.toTradeHistoryUiModel() = TradeHistoryUiModel(
    isBuy = isBuy,
    symbol = symbol,
    money = money,
    tradeDate = tradeDate
)

fun TradeHistoryUiModel.toTradeHistoryDomainModel() = TradeHistoryDomainModel(
    isBuy = isBuy,
    symbol = symbol,
    money = money,
    tradeDate = tradeDate
)
